package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.House;
import com.property.entity.RepairOrder;
import com.property.entity.User;
import com.property.mapper.RepairOrderMapper;
import com.property.mapper.UserMapper;
import com.property.vo.RepairOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairOrderService {

    @Resource
    private RepairOrderMapper repairOrderMapper;

    @Resource
    private HouseService houseService;

    @Resource
    private UserMapper userMapper;

    public PageResult<RepairOrderVO> page(Integer pageNum, Integer pageSize, Long ownerId, Integer status, Long assigneeId, String orderNo) {
        PageHelper.startPage(pageNum, pageSize);
        List<RepairOrder> list = repairOrderMapper.selectPageList(ownerId, status, assigneeId, orderNo);
        PageInfo<RepairOrder> pageInfo = new PageInfo<>(list);
        PageResult<RepairOrderVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public PageResult<RepairOrderVO> myPage(Long ownerId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        return page(pageNum, pageSize, ownerId, status, null, orderNo);
    }

    public void save(RepairOrder order, Long ownerId) {
        if (order == null) {
            throw new BusinessException("报修参数不能为空");
        }
        if (order.getHouseId() == null) {
            throw new BusinessException("请选择房屋");
        }
        House house = houseService.mustGetById(order.getHouseId());
        if (house.getOwnerId() == null || !house.getOwnerId().equals(ownerId)) {
            throw new BusinessException("仅可报修本人房屋");
        }
        if (order.getTitle() == null || order.getTitle().trim().isEmpty()) {
            throw new BusinessException("报修标题不能为空");
        }
        if (order.getContent() == null || order.getContent().trim().isEmpty()) {
            throw new BusinessException("报修内容不能为空");
        }
        order.setOrderNo("REP" + cn.hutool.core.util.IdUtil.getSnowflakeNextIdStr());
        order.setOwnerId(ownerId);
        order.setTitle(order.getTitle().trim());
        order.setContent(order.getContent().trim());
        order.setStatus(0);
        order.setAssigneeId(null);
        order.setReply(null);
        repairOrderMapper.insert(order);
    }

    public void updateStatus(Long id, Integer status, Long assigneeId, String reply) {
        if (status == null || status < 0 || status > 3) {
            throw new BusinessException("状态不合法");
        }
        RepairOrder db = repairOrderMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("报修单不存在");
        }
        Integer current = db.getStatus() == null ? 0 : db.getStatus();
        if (current == 2 || current == 3) {
            throw new BusinessException("当前工单已结束，不可继续处理");
        }
        if (current == 0 && status != 1 && status != 3) {
            throw new BusinessException("待受理工单仅可转为处理中或已取消");
        }
        if (current == 1 && status != 2 && status != 3) {
            throw new BusinessException("处理中工单仅可转为已完成或已取消");
        }
        Long finalAssigneeId = assigneeId == null ? db.getAssigneeId() : assigneeId;
        if ((status == 1 || status == 2) && finalAssigneeId == null) {
            throw new BusinessException("处理中或已完成必须指定处理人");
        }
        String finalReply = reply == null ? null : reply.trim();
        if (status == 2 && (finalReply == null || finalReply.isEmpty())) {
            throw new BusinessException("工单完成时请填写处理结果");
        }
        if (assigneeId != null) {
            User assignee = userMapper.selectById(assigneeId);
            if (assignee == null || (!"STAFF".equals(assignee.getRole()) && !"ADMIN".equals(assignee.getRole()))) {
                throw new BusinessException("指派人员不存在");
            }
        }
        db.setStatus(status);
        db.setAssigneeId(finalAssigneeId);
        db.setReply(finalReply);
        repairOrderMapper.updateStatus(db);
    }

    public void deleteById(Long id, Long userId, String role) {
        RepairOrder db = repairOrderMapper.selectById(id);
        if (db == null) {
            return;
        }
        if ("OWNER".equals(role)) {
            if (!db.getOwnerId().equals(userId)) {
                throw new BusinessException("无权限删除");
            }
            if (db.getStatus() != null && db.getStatus() != 0 && db.getStatus() != 3) {
                throw new BusinessException("当前状态不可删除");
            }
        }
        repairOrderMapper.deleteById(id);
    }

    public Long countPending() {
        Long count = repairOrderMapper.countPending();
        return count == null ? 0L : count;
    }

    public List<Map<String, Object>> statusStats() {
        return repairOrderMapper.statusStats();
    }

    private List<RepairOrderVO> convertList(List<RepairOrder> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, String> houseMap = houseService.buildHouseNameMap();
        List<User> users = userMapper.selectPageList(null, null, null);
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        List<RepairOrderVO> result = new ArrayList<>();
        for (RepairOrder item : list) {
            RepairOrderVO vo = new RepairOrderVO();
            BeanUtils.copyProperties(item, vo);
            vo.setHouseName(houseMap.getOrDefault(item.getHouseId(), "未知房屋"));
            vo.setOwnerName(userMap.getOrDefault(item.getOwnerId(), "未知业主"));
            vo.setAssigneeName(item.getAssigneeId() == null ? "" : userMap.getOrDefault(item.getAssigneeId(), "未知人员"));
            result.add(vo);
        }
        return result;
    }
}

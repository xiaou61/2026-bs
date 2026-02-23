package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.User;
import com.property.entity.VisitorRecord;
import com.property.mapper.UserMapper;
import com.property.mapper.VisitorRecordMapper;
import com.property.vo.VisitorRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorRecordService {

    @Resource
    private VisitorRecordMapper visitorRecordMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<VisitorRecordVO> page(Integer pageNum, Integer pageSize, Long ownerId, Integer status, String orderNo) {
        PageHelper.startPage(pageNum, pageSize);
        List<VisitorRecord> list = visitorRecordMapper.selectPageList(ownerId, status, orderNo);
        PageInfo<VisitorRecord> pageInfo = new PageInfo<>(list);
        PageResult<VisitorRecordVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public PageResult<VisitorRecordVO> myPage(Long ownerId, Integer pageNum, Integer pageSize, Integer status, String orderNo) {
        return page(pageNum, pageSize, ownerId, status, orderNo);
    }

    public void save(VisitorRecord record, Long ownerId) {
        if (record == null) {
            throw new BusinessException("访客参数不能为空");
        }
        if (record.getVisitorName() == null || record.getVisitorName().trim().isEmpty()) {
            throw new BusinessException("访客姓名不能为空");
        }
        if (record.getVisitorPhone() == null || record.getVisitorPhone().trim().isEmpty()) {
            throw new BusinessException("访客电话不能为空");
        }
        if (record.getVisitTime() == null || record.getVisitTime().isBefore(LocalDateTime.now().minusMinutes(1))) {
            throw new BusinessException("来访时间不合法");
        }
        record.setOrderNo("VIS" + cn.hutool.core.util.IdUtil.getSnowflakeNextIdStr());
        record.setOwnerId(ownerId);
        record.setVisitorName(record.getVisitorName().trim());
        record.setVisitorPhone(record.getVisitorPhone().trim());
        record.setRemark(record.getRemark() == null ? null : record.getRemark().trim());
        record.setStatus(0);
        visitorRecordMapper.insert(record);
    }

    public void updateStatus(Long id, Integer status, String remark) {
        if (status == null || (status != 1 && status != 2)) {
            throw new BusinessException("状态不合法");
        }
        VisitorRecord db = visitorRecordMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("访客记录不存在");
        }
        if (db.getStatus() == null || db.getStatus() != 0) {
            throw new BusinessException("该访客记录已审批，不可重复审批");
        }
        db.setStatus(status);
        db.setRemark(remark == null ? null : remark.trim());
        visitorRecordMapper.updateStatus(db);
    }

    public void deleteById(Long id, Long userId, String role) {
        VisitorRecord db = visitorRecordMapper.selectById(id);
        if (db == null) {
            return;
        }
        if ("OWNER".equals(role)) {
            if (!db.getOwnerId().equals(userId)) {
                throw new BusinessException("无权限删除");
            }
            if (db.getStatus() != null && db.getStatus() != 0) {
                throw new BusinessException("已审批记录不可删除");
            }
        }
        visitorRecordMapper.deleteById(id);
    }

    private List<VisitorRecordVO> convertList(List<VisitorRecord> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> users = userMapper.selectPageList(null, null, null);
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        List<VisitorRecordVO> result = new ArrayList<>();
        for (VisitorRecord item : list) {
            VisitorRecordVO vo = new VisitorRecordVO();
            BeanUtils.copyProperties(item, vo);
            vo.setOwnerName(userMap.getOrDefault(item.getOwnerId(), "未知业主"));
            result.add(vo);
        }
        return result;
    }
}

package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.FeeOrder;
import com.property.entity.House;
import com.property.entity.User;
import com.property.mapper.FeeOrderMapper;
import com.property.mapper.UserMapper;
import com.property.vo.FeeOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeeOrderService {

    @Resource
    private FeeOrderMapper feeOrderMapper;

    @Resource
    private HouseService houseService;

    @Resource
    private UserMapper userMapper;

    public PageResult<FeeOrderVO> page(Integer pageNum, Integer pageSize, Long ownerId, Long houseId, Integer status, String feeMonth, String orderNo) {
        PageHelper.startPage(pageNum, pageSize);
        List<FeeOrder> list = feeOrderMapper.selectPageList(ownerId, houseId, status, feeMonth, orderNo);
        PageInfo<FeeOrder> pageInfo = new PageInfo<>(list);
        PageResult<FeeOrderVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public PageResult<FeeOrderVO> myPage(Long ownerId, Integer pageNum, Integer pageSize, Integer status, String feeMonth, String orderNo) {
        return page(pageNum, pageSize, ownerId, null, status, feeMonth, orderNo);
    }

    public void save(FeeOrder order, Long creatorId) {
        if (order == null) {
            throw new BusinessException("账单参数不能为空");
        }
        if (order.getHouseId() == null) {
            throw new BusinessException("请选择房屋");
        }
        House house = houseService.mustGetById(order.getHouseId());
        Long ownerId = order.getOwnerId() == null ? house.getOwnerId() : order.getOwnerId();
        if (ownerId == null) {
            throw new BusinessException("该房屋未绑定业主");
        }
        User owner = userMapper.selectById(ownerId);
        if (owner == null || !"OWNER".equals(owner.getRole())) {
            throw new BusinessException("业主不存在");
        }
        if (order.getAmount() == null || order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("缴费金额必须大于0");
        }
        if (order.getFeeMonth() == null || order.getFeeMonth().trim().isEmpty()) {
            throw new BusinessException("缴费月份不能为空");
        }
        order.setOwnerId(ownerId);
        order.setFeeMonth(order.getFeeMonth().trim());
        order.setRemark(order.getRemark() == null ? null : order.getRemark().trim());
        if (order.getId() == null) {
            order.setOrderNo("FEE" + cn.hutool.core.util.IdUtil.getSnowflakeNextIdStr());
            order.setStatus(0);
            order.setPayTime(null);
            order.setCreatorId(creatorId);
            feeOrderMapper.insert(order);
        } else {
            FeeOrder db = feeOrderMapper.selectById(order.getId());
            if (db == null) {
                throw new BusinessException("账单不存在");
            }
            if (db.getStatus() != null && db.getStatus() == 1) {
                throw new BusinessException("已缴账单不允许修改");
            }
            order.setOrderNo(db.getOrderNo());
            order.setStatus(0);
            order.setPayTime(null);
            order.setCreatorId(db.getCreatorId());
            feeOrderMapper.update(order);
        }
    }

    public void pay(Long id, Long userId, String role) {
        FeeOrder order = feeOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("账单不存在");
        }
        if (order.getStatus() != null && order.getStatus() == 1) {
            throw new BusinessException("账单已缴费");
        }
        if ("OWNER".equals(role) && !order.getOwnerId().equals(userId)) {
            throw new BusinessException("无权限支付该账单");
        }
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        feeOrderMapper.update(order);
    }

    public void deleteById(Long id) {
        FeeOrder db = feeOrderMapper.selectById(id);
        if (db != null && db.getStatus() != null && db.getStatus() == 1) {
            throw new BusinessException("已缴账单不允许删除");
        }
        feeOrderMapper.deleteById(id);
    }

    public Long countUnpaid() {
        Long count = feeOrderMapper.countUnpaid();
        return count == null ? 0L : count;
    }

    public BigDecimal todayPaidAmount() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusDays(1);
        BigDecimal amount = feeOrderMapper.sumPaidAmount(start, end);
        return amount == null ? BigDecimal.ZERO : amount;
    }

    public List<Map<String, Object>> dailyPaidAmount(LocalDateTime start, LocalDateTime end) {
        return feeOrderMapper.dailyPaidAmount(start, end);
    }

    private List<FeeOrderVO> convertList(List<FeeOrder> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, String> houseMap = houseService.buildHouseNameMap();
        List<User> users = userMapper.selectPageList(null, null, null);
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        List<FeeOrderVO> result = new ArrayList<>();
        for (FeeOrder item : list) {
            FeeOrderVO vo = new FeeOrderVO();
            BeanUtils.copyProperties(item, vo);
            vo.setHouseName(houseMap.getOrDefault(item.getHouseId(), "未知房屋"));
            vo.setOwnerName(userMap.getOrDefault(item.getOwnerId(), "未知业主"));
            vo.setCreatorName(userMap.getOrDefault(item.getCreatorId(), "未知用户"));
            result.add(vo);
        }
        return result;
    }
}

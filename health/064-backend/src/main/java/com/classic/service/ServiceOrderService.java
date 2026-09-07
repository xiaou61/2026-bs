package com.classic.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.classic.common.BusinessException;
import com.classic.common.PageResult;
import com.classic.entity.MealPlan;
import com.classic.entity.ServiceOrder;
import com.classic.entity.User;
import com.classic.mapper.MealPlanMapper;
import com.classic.mapper.ServiceOrderMapper;
import com.classic.mapper.UserMapper;
import com.classic.vo.ServiceOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceOrderService {

    @Resource
    private ServiceOrderMapper serviceOrderMapper;

    @Resource
    private MealPlanMapper mealPlanMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<ServiceOrderVO> myPage(Long userId, Integer pageNum, Integer pageSize, String orderNo, Integer status) {
        LambdaQueryWrapper<ServiceOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ServiceOrder::getUserId, userId);
        wrapper.like(orderNo != null && !orderNo.trim().isEmpty(), ServiceOrder::getOrderNo, orderNo == null ? null : orderNo.trim());
        wrapper.eq(status != null, ServiceOrder::getStatus, status);
        wrapper.orderByDesc(ServiceOrder::getId);
        Page<ServiceOrder> page = serviceOrderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<ServiceOrderVO> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(convertList(page.getRecords()));
        return result;
    }

    public PageResult<ServiceOrderVO> page(Integer pageNum, Integer pageSize, String orderNo, Long userId, Integer status) {
        LambdaQueryWrapper<ServiceOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(orderNo != null && !orderNo.trim().isEmpty(), ServiceOrder::getOrderNo, orderNo == null ? null : orderNo.trim());
        wrapper.eq(userId != null, ServiceOrder::getUserId, userId);
        wrapper.eq(status != null, ServiceOrder::getStatus, status);
        wrapper.orderByDesc(ServiceOrder::getId);
        Page<ServiceOrder> page = serviceOrderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        PageResult<ServiceOrderVO> result = new PageResult<>();
        result.setTotal(page.getTotal());
        result.setRecords(convertList(page.getRecords()));
        return result;
    }

    public void save(ServiceOrder order, Long userId) {
        if (order == null) {
            throw new BusinessException("参数不能为空");
        }
        if (order.getPlanId() == null) {
            throw new BusinessException("请选择药膳方案");
        }
        MealPlan mealPlan = mealPlanMapper.selectById(order.getPlanId());
        if (mealPlan == null || mealPlan.getStatus() == null || mealPlan.getStatus() == 0) {
            throw new BusinessException("方案不可用");
        }
        if (order.getContactName() == null || order.getContactName().trim().isEmpty()) {
            throw new BusinessException("联系人不能为空");
        }
        if (order.getContactPhone() == null || order.getContactPhone().trim().isEmpty()) {
            throw new BusinessException("联系电话不能为空");
        }
        if (order.getAppointmentDate() == null || order.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new BusinessException("预约日期不合法");
        }
        ServiceOrder entity = new ServiceOrder();
        entity.setOrderNo("CDS" + IdUtil.getSnowflakeNextIdStr());
        entity.setUserId(userId);
        entity.setPlanId(order.getPlanId());
        entity.setContactName(order.getContactName().trim());
        entity.setContactPhone(order.getContactPhone().trim());
        entity.setAppointmentDate(order.getAppointmentDate());
        entity.setStatus(0);
        entity.setRemark(order.getRemark() == null ? null : order.getRemark().trim());
        serviceOrderMapper.insert(entity);
    }

    public void updateStatus(Long id, Integer status, String adminReply) {
        if (status == null || status < 0 || status > 3) {
            throw new BusinessException("状态不合法");
        }
        ServiceOrder order = serviceOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("服务单不存在");
        }
        order.setStatus(status);
        order.setAdminReply(adminReply == null ? null : adminReply.trim());
        serviceOrderMapper.updateById(order);
    }

    public void deleteById(Long id, Long userId, String role) {
        ServiceOrder order = serviceOrderMapper.selectById(id);
        if (order == null) {
            return;
        }
        if ("ADMIN".equals(role)) {
            serviceOrderMapper.deleteById(id);
            return;
        }
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权限删除");
        }
        if (order.getStatus() != null && order.getStatus() != 0 && order.getStatus() != 3) {
            throw new BusinessException("当前状态不可删除");
        }
        serviceOrderMapper.deleteById(id);
    }

    public Long countAll() {
        return serviceOrderMapper.selectCount(new LambdaQueryWrapper<>());
    }

    public Long countPending() {
        return serviceOrderMapper.selectCount(new LambdaQueryWrapper<ServiceOrder>().eq(ServiceOrder::getStatus, 0));
    }

    public List<Map<String, Object>> dailyCount(LocalDateTime start, LocalDateTime end) {
        return serviceOrderMapper.dailyCount(start, end);
    }

    public List<Map<String, Object>> statusStats() {
        return serviceOrderMapper.statusStats();
    }

    private List<ServiceOrderVO> convertList(List<ServiceOrder> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<>());
        List<MealPlan> plans = mealPlanMapper.selectList(new LambdaQueryWrapper<>());
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname());
        }
        Map<Long, String> planMap = new HashMap<>();
        for (MealPlan plan : plans) {
            planMap.put(plan.getId(), plan.getName());
        }
        List<ServiceOrderVO> result = new ArrayList<>();
        for (ServiceOrder item : list) {
            ServiceOrderVO vo = new ServiceOrderVO();
            BeanUtils.copyProperties(item, vo);
            vo.setUserName(userMap.getOrDefault(item.getUserId(), "未知用户"));
            vo.setPlanName(planMap.getOrDefault(item.getPlanId(), "未知方案"));
            result.add(vo);
        }
        return result;
    }
}

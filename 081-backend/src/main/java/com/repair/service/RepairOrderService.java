package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.BusinessException;
import com.repair.entity.RepairOrder;
import com.repair.entity.RepairProcess;
import com.repair.mapper.RepairOrderMapper;
import com.repair.mapper.RepairProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class RepairOrderService {

    @Autowired
    private RepairOrderMapper orderMapper;

    @Autowired
    private RepairProcessMapper processMapper;

    public Page<RepairOrder> getList(int pageNum, int pageSize, String orderNo, Integer status, Long technicianId, String contactPhone, Long userId) {
        Page<RepairOrder> page = new Page<>(pageNum, pageSize);
        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(orderNo)) {
            wrapper.like("order_no", orderNo);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (technicianId != null) {
            wrapper.eq("technician_id", technicianId);
        }
        if (StringUtils.hasText(contactPhone)) {
            wrapper.like("contact_phone", contactPhone);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        wrapper.orderByDesc("create_time");
        return orderMapper.selectPage(page, wrapper);
    }

    public RepairOrder getDetail(Long id) {
        return orderMapper.selectById(id);
    }

    public void add(RepairOrder order) {
        if (!StringUtils.hasText(order.getOrderNo())) {
            order.setOrderNo(generateOrderNo());
        }
        if (order.getStatus() == null) {
            order.setStatus(0);
        }
        if (order.getPayStatus() == null) {
            order.setPayStatus(0);
        }
        orderMapper.insert(order);
    }

    public void update(RepairOrder order) {
        orderMapper.updateById(order);
    }

    public void delete(Long id) {
        orderMapper.deleteById(id);
    }

    public void assign(Long orderId, Long technicianId, Long operatorId) {
        RepairOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("工单不存在");
        }
        order.setTechnicianId(technicianId);
        order.setStatus(1);
        orderMapper.updateById(order);
        addProcess(orderId, operatorId, "admin", "assign", "平台已完成派单");
    }

    public void updateStatus(Long orderId, Integer status, String content, Long operatorId, String operatorRole) {
        RepairOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("工单不存在");
        }
        order.setStatus(status);
        if (status != null && status == 4) {
            order.setFinishTime(LocalDateTime.now());
        }
        orderMapper.updateById(order);
        String logContent = StringUtils.hasText(content) ? content : "工单状态更新";
        addProcess(orderId, operatorId, operatorRole, "status", logContent);
    }

    public Page<RepairOrder> getUserOrders(int pageNum, int pageSize, Long userId, Integer status) {
        return getList(pageNum, pageSize, null, status, null, null, userId);
    }

    public Page<RepairOrder> getTechnicianOrders(int pageNum, int pageSize, Long technicianId, Integer status) {
        return getList(pageNum, pageSize, null, status, technicianId, null, null);
    }

    public void createByUser(RepairOrder order, Long userId) {
        order.setUserId(userId);
        if (!StringUtils.hasText(order.getOrderNo())) {
            order.setOrderNo(generateOrderNo());
        }
        if (order.getStatus() == null) {
            order.setStatus(0);
        }
        if (order.getPayStatus() == null) {
            order.setPayStatus(0);
        }
        orderMapper.insert(order);
        addProcess(order.getId(), userId, "customer", "create", "用户提交报修工单");
    }

    public void cancelByUser(Long orderId, Long userId) {
        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId).eq("user_id", userId);
        RepairOrder order = orderMapper.selectOne(wrapper);
        if (order == null) {
            throw new BusinessException("工单不存在");
        }
        if (order.getStatus() != null && order.getStatus() == 4) {
            throw new BusinessException("已完成工单不可取消");
        }
        order.setStatus(5);
        orderMapper.updateById(order);
        addProcess(orderId, userId, "customer", "cancel", "用户取消工单");
    }

    public void payByUser(Long orderId, Long userId, String content) {
        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId).eq("user_id", userId);
        RepairOrder order = orderMapper.selectOne(wrapper);
        if (order == null) {
            throw new BusinessException("工单不存在");
        }
        if (order.getStatus() != null && order.getStatus() == 5) {
            throw new BusinessException("已取消工单不可支付");
        }
        order.setPayStatus(1);
        if (order.getStatus() != null && order.getStatus() == 3) {
            order.setStatus(4);
            order.setFinishTime(LocalDateTime.now());
        }
        orderMapper.updateById(order);
        addProcess(orderId, userId, "customer", "pay", StringUtils.hasText(content) ? content : "用户完成支付");
    }

    public void technicianUpdateStatus(Long orderId, Long technicianId, Long operatorId, Integer status, String content) {
        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId).eq("technician_id", technicianId);
        RepairOrder order = orderMapper.selectOne(wrapper);
        if (order == null) {
            throw new BusinessException("工单不存在或未分配给当前技师");
        }
        if (order.getStatus() != null && order.getStatus() == 5) {
            throw new BusinessException("已取消工单不可操作");
        }
        order.setStatus(status);
        if (status != null && status == 4) {
            order.setFinishTime(LocalDateTime.now());
        }
        orderMapper.updateById(order);
        addProcess(orderId, operatorId, "technician", "status", StringUtils.hasText(content) ? content : "技师更新工单状态");
    }

    public void technicianAddProcess(Long orderId, Long technicianId, Long operatorId, String content, String images) {
        QueryWrapper<RepairOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId).eq("technician_id", technicianId);
        RepairOrder order = orderMapper.selectOne(wrapper);
        if (order == null) {
            throw new BusinessException("工单不存在或未分配给当前技师");
        }
        RepairProcess process = new RepairProcess();
        process.setOrderId(orderId);
        process.setOperatorId(operatorId);
        process.setOperatorRole("technician");
        process.setNodeType("progress");
        process.setContent(StringUtils.hasText(content) ? content : "技师更新处理进度");
        process.setImages(images);
        processMapper.insert(process);
    }

    private String generateOrderNo() {
        return "RO" + System.currentTimeMillis();
    }

    private void addProcess(Long orderId, Long operatorId, String operatorRole, String nodeType, String content) {
        RepairProcess process = new RepairProcess();
        process.setOrderId(orderId);
        process.setOperatorId(operatorId);
        process.setOperatorRole(operatorRole);
        process.setNodeType(nodeType);
        process.setContent(content);
        processMapper.insert(process);
    }
}

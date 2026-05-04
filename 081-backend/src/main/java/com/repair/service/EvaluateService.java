package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.BusinessException;
import com.repair.entity.Evaluate;
import com.repair.entity.RepairOrder;
import com.repair.mapper.EvaluateMapper;
import com.repair.mapper.RepairOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluateService {

    @Autowired
    private EvaluateMapper evaluateMapper;

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    public Page<Evaluate> getList(int pageNum, int pageSize, Long orderId, Long technicianId, Integer score) {
        Page<Evaluate> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        if (orderId != null) {
            wrapper.eq("order_id", orderId);
        }
        if (technicianId != null) {
            wrapper.eq("technician_id", technicianId);
        }
        if (score != null) {
            wrapper.eq("score", score);
        }
        wrapper.orderByDesc("create_time");
        return evaluateMapper.selectPage(page, wrapper);
    }

    public void add(Evaluate evaluate) {
        evaluateMapper.insert(evaluate);
    }

    public void userSubmit(Evaluate evaluate, Long userId) {
        RepairOrder order = repairOrderMapper.selectById(evaluate.getOrderId());
        if (order == null || order.getUserId() == null || !order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权评价该工单");
        }
        if (order.getStatus() == null || order.getStatus() != 4) {
            throw new BusinessException(400, "工单完成后才能评价");
        }
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", evaluate.getOrderId()).eq("user_id", userId);
        if (evaluateMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(400, "该工单已评价");
        }
        evaluate.setUserId(userId);
        if (evaluate.getTechnicianId() == null) {
            evaluate.setTechnicianId(order.getTechnicianId());
        }
        evaluateMapper.insert(evaluate);
    }

    public Page<Evaluate> getMyList(int pageNum, int pageSize, Long userId) {
        Page<Evaluate> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByDesc("create_time");
        return evaluateMapper.selectPage(page, wrapper);
    }
}

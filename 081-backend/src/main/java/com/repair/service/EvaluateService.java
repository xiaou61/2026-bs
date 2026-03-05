package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.BusinessException;
import com.repair.entity.Evaluate;
import com.repair.mapper.EvaluateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluateService {

    @Autowired
    private EvaluateMapper evaluateMapper;

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
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", evaluate.getOrderId()).eq("user_id", userId);
        if (evaluateMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("该工单已评价");
        }
        evaluate.setUserId(userId);
        evaluateMapper.insert(evaluate);
    }

    public Page<Evaluate> getMyList(int pageNum, int pageSize, Long userId) {
        Page<Evaluate> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByDesc("create_time");
        return evaluateMapper.selectPage(page, wrapper);
    }
}

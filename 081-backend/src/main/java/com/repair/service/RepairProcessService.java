package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.repair.entity.RepairProcess;
import com.repair.mapper.RepairProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairProcessService {

    @Autowired
    private RepairProcessMapper processMapper;

    public List<RepairProcess> getByOrderId(Long orderId) {
        QueryWrapper<RepairProcess> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId).orderByAsc("create_time");
        return processMapper.selectList(wrapper);
    }

    public void add(RepairProcess process) {
        processMapper.insert(process);
    }
}

package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.entity.Technician;
import com.repair.mapper.TechnicianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TechnicianService {

    @Autowired
    private TechnicianMapper technicianMapper;

    public Page<Technician> getList(int pageNum, int pageSize, String name, String level, Integer workStatus) {
        Page<Technician> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Technician> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like("name", name);
        }
        if (StringUtils.hasText(level)) {
            wrapper.eq("level", level);
        }
        if (workStatus != null) {
            wrapper.eq("work_status", workStatus);
        }
        wrapper.orderByDesc("rating").orderByDesc("create_time");
        return technicianMapper.selectPage(page, wrapper);
    }

    public Technician getByUserId(Long userId) {
        QueryWrapper<Technician> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return technicianMapper.selectOne(wrapper);
    }

    public void add(Technician technician) {
        technicianMapper.insert(technician);
    }

    public void update(Technician technician) {
        technicianMapper.updateById(technician);
    }

    public void delete(Long id) {
        technicianMapper.deleteById(id);
    }
}

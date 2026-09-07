package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.entity.Child;
import com.repair.mapper.ChildMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {

    @Autowired
    private ChildMapper childMapper;

    public Page<Child> getList(int pageNum, int pageSize, String name, String province, String city, Integer sponsorStatus) {
        Page<Child> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Child> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (province != null && !province.isEmpty()) {
            wrapper.eq("province", province);
        }
        if (city != null && !city.isEmpty()) {
            wrapper.eq("city", city);
        }
        if (sponsorStatus != null) {
            wrapper.eq("sponsor_status", sponsorStatus);
        }
        return childMapper.selectPage(page, wrapper);
    }

    public Child getDetail(Long id) {
        return childMapper.selectById(id);
    }

    public void add(Child child) {
        childMapper.insert(child);
    }

    public void update(Child child) {
        childMapper.updateById(child);
    }

    public void delete(Long id) {
        childMapper.deleteById(id);
    }
}


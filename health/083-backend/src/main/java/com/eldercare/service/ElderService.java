package com.eldercare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.BusinessException;
import com.eldercare.entity.ElderProfile;
import com.eldercare.mapper.ElderProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ElderService {

    @Autowired
    private ElderProfileMapper elderProfileMapper;

    public Page<ElderProfile> page(int pageNum, int pageSize, String name, String phone, Integer status) {
        Page<ElderProfile> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ElderProfile> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like("name", name);
        }
        if (StringUtils.hasText(phone)) {
            wrapper.like("phone", phone);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return elderProfileMapper.selectPage(page, wrapper);
    }

    public List<ElderProfile> listAll() {
        QueryWrapper<ElderProfile> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).orderByAsc("id");
        return elderProfileMapper.selectList(wrapper);
    }

    public void add(ElderProfile elderProfile) {
        if (!StringUtils.hasText(elderProfile.getName())) {
            throw new BusinessException("老人姓名不能为空");
        }
        if (!StringUtils.hasText(elderProfile.getPhone())) {
            throw new BusinessException("手机号不能为空");
        }
        if (elderProfile.getStatus() == null) {
            elderProfile.setStatus(1);
        }
        elderProfileMapper.insert(elderProfile);
    }

    public void update(ElderProfile elderProfile) {
        elderProfileMapper.updateById(elderProfile);
    }

    public void delete(Long id) {
        elderProfileMapper.deleteById(id);
    }
}

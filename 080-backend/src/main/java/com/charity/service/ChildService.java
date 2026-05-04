package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.Child;
import com.charity.mapper.ChildMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {

    @Autowired
    private ChildMapper childMapper;

    @Autowired
    private UserService userService;

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
        Child child = childMapper.selectById(id);
        if (child == null) {
            throw new BusinessException(404, "儿童信息不存在");
        }
        return child;
    }

    public void add(Child child, Long currentUserId) {
        userService.requireAnyRole(currentUserId, "volunteer");
        if (child.getSponsorStatus() == null) {
            child.setSponsorStatus(0);
        }
        childMapper.insert(child);
    }

    public void update(Child child, Long currentUserId) {
        userService.requireAnyRole(currentUserId, "volunteer");
        getDetail(child.getId());
        childMapper.updateById(child);
    }

    public void delete(Long id, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        childMapper.deleteById(id);
    }
}

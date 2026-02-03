package com.enrollment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.common.BusinessException;
import com.enrollment.entity.Admin;
import com.enrollment.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public IPage<Admin> getPage(Integer pageNum, Integer pageSize, String username, String name) {
        Page<Admin> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            wrapper.like("username", username);
        }
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        IPage<Admin> result = adminMapper.selectPage(page, wrapper);
        result.getRecords().forEach(admin -> admin.setPassword(null));
        return result;
    }

    public void add(Admin admin) {
        Admin exist = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", admin.getUsername()));
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            admin.setPassword("123456");
        }
        adminMapper.insert(admin);
    }

    public void update(Admin admin) {
        Admin exist = adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username", admin.getUsername())
                .ne("id", admin.getId()));
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        Admin old = adminMapper.selectById(admin.getId());
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            admin.setPassword(old.getPassword());
        }
        adminMapper.updateById(admin);
    }

    public void delete(Long id) {
        adminMapper.deleteById(id);
    }
}

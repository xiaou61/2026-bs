package com.enrollment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Department;
import com.enrollment.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public IPage<Department> getPage(Integer pageNum, Integer pageSize, String name) {
        Page<Department> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        wrapper.orderByAsc("sort");
        return departmentMapper.selectPage(page, wrapper);
    }

    public List<Department> getList() {
        return departmentMapper.selectList(new QueryWrapper<Department>().eq("status", 1).orderByAsc("sort"));
    }

    public void add(Department department) {
        departmentMapper.insert(department);
    }

    public void update(Department department) {
        departmentMapper.updateById(department);
    }

    public void delete(Long id) {
        departmentMapper.deleteById(id);
    }
}

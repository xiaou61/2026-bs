package com.enrollment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Major;
import com.enrollment.mapper.MajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {

    @Autowired
    private MajorMapper majorMapper;

    public IPage<Major> getPage(Integer pageNum, Integer pageSize, String name, Long departmentId) {
        Page<Major> page = new Page<>(pageNum, pageSize);
        return majorMapper.selectPageWithDept(page, name, departmentId);
    }

    public List<Major> getListByDepartment(Long departmentId) {
        QueryWrapper<Major> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (departmentId != null) {
            wrapper.eq("department_id", departmentId);
        }
        return majorMapper.selectList(wrapper);
    }

    public List<Major> getAll() {
        return majorMapper.selectList(new QueryWrapper<Major>().eq("status", 1));
    }

    public void add(Major major) {
        majorMapper.insert(major);
    }

    public void update(Major major) {
        majorMapper.updateById(major);
    }

    public void delete(Long id) {
        majorMapper.deleteById(id);
    }
}

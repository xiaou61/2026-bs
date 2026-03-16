package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.DepartmentInfo;
import com.course.mapper.DepartmentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentInfoMapper departmentMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<DepartmentInfo> list(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DepartmentInfo> list = departmentMapper.selectList(name, status);
        return new PageInfo<>(list);
    }

    public List<DepartmentInfo> enabledList() {
        return departmentMapper.selectEnabled();
    }

    public void add(DepartmentInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        departmentMapper.insert(entity);
    }

    public void update(DepartmentInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("院系ID不能为空");
        }
        validate(entity);
        departmentMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        departmentMapper.deleteById(id);
    }

    private void validate(DepartmentInfo entity) {
        if (entity == null || !StringUtils.hasText(entity.getName()) || !StringUtils.hasText(entity.getCode())) {
            throw new BusinessException("院系名称和编码不能为空");
        }
    }
}

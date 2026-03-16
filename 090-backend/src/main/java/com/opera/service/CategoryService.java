package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.OperaCategory;
import com.opera.mapper.OperaCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private OperaCategoryMapper departmentMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<OperaCategory> list(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OperaCategory> list = departmentMapper.selectList(name, status);
        return new PageInfo<>(list);
    }

    public List<OperaCategory> enabledList() {
        return departmentMapper.selectEnabled();
    }

    public void add(OperaCategory entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        departmentMapper.insert(entity);
    }

    public void update(OperaCategory entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("剧种分类ID不能为空");
        }
        validate(entity);
        departmentMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        departmentMapper.deleteById(id);
    }

    private void validate(OperaCategory entity) {
        if (entity == null || !StringUtils.hasText(entity.getName()) || !StringUtils.hasText(entity.getCode())) {
            throw new BusinessException("剧种分类名称和编码不能为空");
        }
    }
}



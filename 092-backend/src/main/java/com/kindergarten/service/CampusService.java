package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.CampusInfo;
import com.kindergarten.mapper.CampusInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CampusService {

    @Autowired
    private CampusInfoMapper departmentMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<CampusInfo> list(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CampusInfo> list = departmentMapper.selectList(name, status);
        return new PageInfo<>(list);
    }

    public List<CampusInfo> enabledList() {
        return departmentMapper.selectEnabled();
    }

    public void add(CampusInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        departmentMapper.insert(entity);
    }

    public void update(CampusInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("园区ID不能为空");
        }
        validate(entity);
        departmentMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        departmentMapper.deleteById(id);
    }

    private void validate(CampusInfo entity) {
        if (entity == null || !StringUtils.hasText(entity.getName()) || !StringUtils.hasText(entity.getCode())) {
            throw new BusinessException("园区名称和编码不能为空");
        }
    }
}

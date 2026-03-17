package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.GradeInfo;
import com.kindergarten.mapper.GradeInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeInfoMapper majorMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<GradeInfo> list(String name, Long departmentId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GradeInfo> list = majorMapper.selectList(name, departmentId, status);
        return new PageInfo<>(list);
    }

    public List<GradeInfo> enabledList() {
        return majorMapper.selectEnabled();
    }

    public void add(GradeInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        majorMapper.insert(entity);
    }

    public void update(GradeInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("年级ID不能为空");
        }
        validate(entity);
        majorMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        majorMapper.deleteById(id);
    }

    private void validate(GradeInfo entity) {
        if (entity == null || entity.getDepartmentId() == null || !StringUtils.hasText(entity.getName()) || !StringUtils.hasText(entity.getCode())) {
            throw new BusinessException("园区、年级名称和编码不能为空");
        }
    }
}

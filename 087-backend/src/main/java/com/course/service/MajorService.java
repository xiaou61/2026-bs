package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.MajorInfo;
import com.course.mapper.MajorInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MajorService {

    @Autowired
    private MajorInfoMapper majorMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<MajorInfo> list(String name, Long departmentId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MajorInfo> list = majorMapper.selectList(name, departmentId, status);
        return new PageInfo<>(list);
    }

    public List<MajorInfo> enabledList() {
        return majorMapper.selectEnabled();
    }

    public void add(MajorInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        majorMapper.insert(entity);
    }

    public void update(MajorInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("专业ID不能为空");
        }
        validate(entity);
        majorMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        majorMapper.deleteById(id);
    }

    private void validate(MajorInfo entity) {
        if (entity == null || entity.getDepartmentId() == null || !StringUtils.hasText(entity.getName()) || !StringUtils.hasText(entity.getCode())) {
            throw new BusinessException("院系、专业名称和编码不能为空");
        }
    }
}

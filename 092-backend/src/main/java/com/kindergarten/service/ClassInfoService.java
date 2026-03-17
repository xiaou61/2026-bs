package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.ClassInfo;
import com.kindergarten.mapper.ClassInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClassInfoService {

    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<ClassInfo> list(String name, Long majorId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ClassInfo> list = classInfoMapper.selectList(name, majorId, status);
        return new PageInfo<>(list);
    }

    public List<ClassInfo> enabledList() {
        return classInfoMapper.selectEnabled();
    }

    public void add(ClassInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStudentCount() == null) {
            entity.setStudentCount(0);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        classInfoMapper.insert(entity);
    }

    public void update(ClassInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("班级ID不能为空");
        }
        validate(entity);
        classInfoMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        classInfoMapper.deleteById(id);
    }

    private void validate(ClassInfo entity) {
        if (entity == null || entity.getMajorId() == null || !StringUtils.hasText(entity.getName())) {
            throw new BusinessException("年级和班级名称不能为空");
        }
    }
}

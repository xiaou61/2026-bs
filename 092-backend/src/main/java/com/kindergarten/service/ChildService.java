package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.ChildProfile;
import com.kindergarten.mapper.ChildProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildProfileMapper childProfileMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<ChildProfile> list(Long classId, Long parentId, Integer profileStatus, String role, Long currentUserId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long targetParentId = "parent".equals(role) ? currentUserId : parentId;
        Long teacherId = "teacher".equals(role) ? currentUserId : null;
        List<ChildProfile> list = childProfileMapper.selectList(classId, targetParentId, profileStatus, teacherId);
        return new PageInfo<>(list);
    }

    public List<ChildProfile> myChildren(Long parentId) {
        return childProfileMapper.selectByParentId(parentId);
    }

    public void add(ChildProfile entity, String role, Long currentUserId) {
        authService.assertTeacher(role);
        validate(entity);
        if ("teacher".equals(role)) {
            entity.setTeacherId(currentUserId);
        }
        if (entity.getProfileStatus() == null) {
            entity.setProfileStatus(1);
        }
        childProfileMapper.insert(entity);
    }

    public void update(ChildProfile entity, String role, Long currentUserId) {
        authService.assertTeacher(role);
        if (entity.getId() == null) {
            throw new BusinessException("幼儿档案ID不能为空");
        }
        validate(entity);
        ChildProfile current = requireChild(entity.getId());
        if ("teacher".equals(role) && !currentUserId.equals(current.getTeacherId())) {
            throw new BusinessException(403, "无权限操作其他班级幼儿档案");
        }
        if ("teacher".equals(role)) {
            entity.setTeacherId(currentUserId);
        }
        childProfileMapper.update(entity);
    }

    public void delete(Long id, String role, Long currentUserId) {
        authService.assertTeacher(role);
        ChildProfile current = requireChild(id);
        if ("teacher".equals(role) && !currentUserId.equals(current.getTeacherId())) {
            throw new BusinessException(403, "无权限操作其他班级幼儿档案");
        }
        childProfileMapper.deleteById(id);
    }

    public ChildProfile requireChild(Long id) {
        ChildProfile entity = childProfileMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("幼儿档案不存在");
        }
        return entity;
    }

    public ChildProfile requireChildByParentId(Long parentId) {
        List<ChildProfile> children = childProfileMapper.selectByParentId(parentId);
        if (children == null || children.isEmpty()) {
            throw new BusinessException("幼儿档案不存在");
        }
        return children.get(0);
    }

    private void validate(ChildProfile entity) {
        if (entity == null || !StringUtils.hasText(entity.getChildName()) || entity.getClassId() == null || entity.getParentId() == null) {
            throw new BusinessException("幼儿姓名、所属班级和家长账号不能为空");
        }
    }
}

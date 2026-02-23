package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.TeachingClass;
import com.teacher.new.mapper.TeachingClassMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeachingClassService {

    @Resource
    private TeachingClassMapper teachingClassMapper;

    public PageResult<TeachingClass> page(Integer pageNum, Integer pageSize, String gradeName, String className, Integer status) {
        Page<TeachingClass> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TeachingClass> wrapper = new LambdaQueryWrapper<TeachingClass>()
                .like(StringUtils.hasText(gradeName), TeachingClass::getGradeName, gradeName == null ? null : gradeName.trim())
                .like(StringUtils.hasText(className), TeachingClass::getClassName, className == null ? null : className.trim())
                .eq(status != null, TeachingClass::getStatus, status)
                .orderByAsc(TeachingClass::getId);
        Page<TeachingClass> resultPage = teachingClassMapper.selectPage(page, wrapper);
        PageResult<TeachingClass> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<TeachingClass> list() {
        return teachingClassMapper.selectList(new LambdaQueryWrapper<TeachingClass>()
                .eq(TeachingClass::getStatus, 1)
                .orderByAsc(TeachingClass::getId));
    }

    public void save(TeachingClass teachingClass) {
        if (teachingClass == null || !StringUtils.hasText(teachingClass.getGradeName()) || !StringUtils.hasText(teachingClass.getClassName())) {
            throw new BusinessException("班级参数不完整");
        }
        if (teachingClass.getId() == null) {
            add(teachingClass);
        } else {
            update(teachingClass);
        }
    }

    private void add(TeachingClass teachingClass) {
        String gradeName = teachingClass.getGradeName().trim();
        String className = teachingClass.getClassName().trim();
        TeachingClass exist = teachingClassMapper.selectOne(new LambdaQueryWrapper<TeachingClass>()
                .eq(TeachingClass::getGradeName, gradeName)
                .eq(TeachingClass::getClassName, className)
                .last("limit 1"));
        if (exist != null) {
            throw new BusinessException("班级已存在");
        }
        teachingClass.setGradeName(gradeName);
        teachingClass.setClassName(className);
        teachingClass.setHeadTeacher(StringUtils.hasText(teachingClass.getHeadTeacher()) ? teachingClass.getHeadTeacher().trim() : "");
        teachingClass.setStatus(teachingClass.getStatus() == null ? 1 : (teachingClass.getStatus() == 0 ? 0 : 1));
        teachingClassMapper.insert(teachingClass);
    }

    private void update(TeachingClass teachingClass) {
        TeachingClass db = teachingClassMapper.selectById(teachingClass.getId());
        if (db == null) {
            throw new BusinessException("班级不存在");
        }
        String gradeName = teachingClass.getGradeName().trim();
        String className = teachingClass.getClassName().trim();
        TeachingClass exist = teachingClassMapper.selectOne(new LambdaQueryWrapper<TeachingClass>()
                .eq(TeachingClass::getGradeName, gradeName)
                .eq(TeachingClass::getClassName, className)
                .last("limit 1"));
        if (exist != null && !exist.getId().equals(teachingClass.getId())) {
            throw new BusinessException("班级已存在");
        }
        db.setGradeName(gradeName);
        db.setClassName(className);
        db.setHeadTeacher(StringUtils.hasText(teachingClass.getHeadTeacher()) ? teachingClass.getHeadTeacher().trim() : "");
        if (teachingClass.getStatus() != null) {
            db.setStatus(teachingClass.getStatus() == 0 ? 0 : 1);
        }
        teachingClassMapper.updateById(db);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        TeachingClass db = teachingClassMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("班级不存在");
        }
        db.setStatus(status);
        teachingClassMapper.updateById(db);
    }

    public void deleteById(Long id) {
        teachingClassMapper.deleteById(id);
    }

    public Long countAll() {
        return teachingClassMapper.selectCount(null);
    }

    public Map<Long, TeachingClass> mapByIds(Collection<Long> ids) {
        Map<Long, TeachingClass> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        Set<Long> idSet = ids.stream().filter(java.util.Objects::nonNull).collect(Collectors.toSet());
        if (idSet.isEmpty()) {
            return map;
        }
        List<TeachingClass> list = teachingClassMapper.selectBatchIds(idSet);
        for (TeachingClass item : list) {
            map.put(item.getId(), item);
        }
        return map;
    }

    public String classFullName(TeachingClass teachingClass) {
        if (teachingClass == null) {
            return "";
        }
        return (teachingClass.getGradeName() == null ? "" : teachingClass.getGradeName()) + (teachingClass.getClassName() == null ? "" : teachingClass.getClassName());
    }
}

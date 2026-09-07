package com.alumni.service;

import com.alumni.entity.ClassInfo;
import com.alumni.mapper.ClassInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public List<ClassInfo> list(Long gradeId) {
        LambdaQueryWrapper<ClassInfo> wrapper = new LambdaQueryWrapper<>();
        if (gradeId != null) {
            wrapper.eq(ClassInfo::getGradeId, gradeId);
        }
        wrapper.orderByAsc(ClassInfo::getName);
        return classInfoMapper.selectList(wrapper);
    }

    public void add(ClassInfo classInfo) {
        classInfoMapper.insert(classInfo);
    }

    public void update(ClassInfo classInfo) {
        classInfoMapper.updateById(classInfo);
    }

    public void delete(Long id) {
        classInfoMapper.deleteById(id);
    }
}

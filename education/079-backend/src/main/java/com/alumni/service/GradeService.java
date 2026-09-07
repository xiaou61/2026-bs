package com.alumni.service;

import com.alumni.entity.Grade;
import com.alumni.mapper.GradeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    public List<Grade> list() {
        return gradeMapper.selectList(new LambdaQueryWrapper<Grade>().orderByDesc(Grade::getYear));
    }

    public void add(Grade grade) {
        gradeMapper.insert(grade);
    }

    public void update(Grade grade) {
        gradeMapper.updateById(grade);
    }

    public void delete(Long id) {
        gradeMapper.deleteById(id);
    }
}

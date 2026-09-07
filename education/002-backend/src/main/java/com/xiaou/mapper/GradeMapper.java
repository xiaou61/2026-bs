package com.xiaou.mapper;

import com.xiaou.entity.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GradeMapper {
    Grade findById(Long id);
    
    Grade findByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
    
    List<Grade> findByStudentId(Long studentId);
    
    List<Grade> findByCourseId(Long courseId);
    
    List<Grade> findByTeacherId(Long teacherId);
    
    int insert(Grade grade);
    
    int update(Grade grade);
    
    int deleteById(Long id);
}


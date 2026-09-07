package com.xiaou.mapper;

import com.xiaou.entity.CourseSelection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseSelectionMapper {
    CourseSelection findById(Long id);
    
    CourseSelection findByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
    
    List<CourseSelection> findByStudentId(Long studentId);
    
    List<CourseSelection> findByCourseId(Long courseId);
    
    int insert(CourseSelection selection);
    
    int update(CourseSelection selection);
    
    int deleteById(Long id);
    
    int deleteByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}


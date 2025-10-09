package com.xiaou.mapper;

import com.xiaou.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    Course findById(Long id);
    
    Course findByCourseNo(String courseNo);
    
    List<Course> findAll(@Param("keyword") String keyword, @Param("teacherId") Long teacherId, @Param("courseType") String courseType);
    
    List<Course> findByTeacherId(Long teacherId);
    
    int insert(Course course);
    
    int update(Course course);
    
    int deleteById(Long id);
    
    int updateSelectedCount(@Param("courseId") Long courseId, @Param("increment") int increment);
}


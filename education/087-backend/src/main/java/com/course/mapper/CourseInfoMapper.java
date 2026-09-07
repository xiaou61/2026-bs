package com.course.mapper;

import com.course.entity.CourseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseInfoMapper {
    List<CourseInfo> selectList(@Param("courseName") String courseName, @Param("teacherId") Long teacherId, @Param("termId") Long termId, @Param("status") Integer status);
    CourseInfo selectById(@Param("id") Long id);
    List<CourseInfo> selectEnabled();
    long countAll();
    int insert(CourseInfo entity);
    int update(CourseInfo entity);
    int deleteById(@Param("id") Long id);
}

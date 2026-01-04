package com.programming.learning.mapper;

import com.programming.learning.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程Mapper接口
 */
@Mapper
public interface CourseMapper {

    int insert(Course course);

    int deleteById(Long id);

    int update(Course course);

    Course selectById(Long id);

    List<Course> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

    Long countAll();

    List<Course> selectByCategory(@Param("category") String category, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Course> selectByTeacherId(@Param("teacherId") Long teacherId);

    List<Course> selectHot(@Param("limit") Integer limit);

    int incrementViewCount(Long id);

    int incrementLikeCount(Long id);

    int decrementLikeCount(Long id);

    int incrementFavoriteCount(Long id);

    int decrementFavoriteCount(Long id);

    int incrementStudentCount(Long id);
}

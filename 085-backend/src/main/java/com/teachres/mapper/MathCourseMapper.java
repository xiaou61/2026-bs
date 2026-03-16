package com.teachres.mapper;

import com.teachres.entity.MathCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MathCourseMapper {
    MathCourse selectById(@Param("id") Long id);

    List<MathCourse> selectList(@Param("courseName") String courseName,
                                @Param("categoryId") Long categoryId,
                                @Param("teacherId") Long teacherId,
                                @Param("status") Integer status,
                                @Param("term") String term);

    List<MathCourse> selectEnabledList();

    int countByCode(@Param("courseCode") String courseCode);

    int insert(MathCourse course);

    int update(MathCourse course);

    int deleteById(@Param("id") Long id);

    long countAll();
}

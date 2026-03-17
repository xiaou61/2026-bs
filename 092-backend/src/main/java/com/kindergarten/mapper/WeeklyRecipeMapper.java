package com.kindergarten.mapper;

import com.kindergarten.entity.WeeklyRecipe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeeklyRecipeMapper {
    List<WeeklyRecipe> selectList(@Param("scheduleId") Long scheduleId, @Param("teacherId") Long teacherId, @Param("keyword") String keyword);
    List<WeeklyRecipe> selectStudentList(@Param("studentId") Long studentId, @Param("keyword") String keyword);
    WeeklyRecipe selectById(@Param("id") Long id);
    int insert(WeeklyRecipe entity);
    int update(WeeklyRecipe entity);
    int deleteById(@Param("id") Long id);
}

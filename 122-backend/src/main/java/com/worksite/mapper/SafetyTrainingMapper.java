package com.worksite.mapper;

import com.worksite.entity.SafetyTraining;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SafetyTrainingMapper {
    @Select({
        "<script>",
        "SELECT * FROM safety_training",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (training_no LIKE CONCAT('%',#{keyword},'%') OR course_name LIKE CONCAT('%',#{keyword},'%') OR trainer_name LIKE CONCAT('%',#{keyword},'%') OR team_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SafetyTraining> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM safety_training WHERE id = #{id}")
    SafetyTraining selectById(Long id);

    @Insert("INSERT INTO safety_training (training_no, course_name, trainer_name, team_name, trainee_count, train_date, status, created_time, updated_time) VALUES (#{trainingNo}, #{courseName}, #{trainerName}, #{teamName}, #{traineeCount}, #{trainDate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SafetyTraining entity);

    @Update("UPDATE safety_training SET training_no = #{trainingNo}, course_name = #{courseName}, trainer_name = #{trainerName}, team_name = #{teamName}, trainee_count = #{traineeCount}, train_date = #{trainDate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SafetyTraining entity);

    @Delete("DELETE FROM safety_training WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM safety_training")
    long countAll();

    @Update("UPDATE safety_training SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

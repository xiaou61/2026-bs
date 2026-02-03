package com.enrollment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Score;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ScoreMapper extends BaseMapper<Score> {
    @Select("SELECT sc.*, s.name as student_name, s.exam_no FROM score sc " +
            "LEFT JOIN student s ON sc.student_id = s.id " +
            "WHERE (#{studentName} IS NULL OR s.name LIKE CONCAT('%', #{studentName}, '%')) " +
            "AND (#{year} IS NULL OR sc.year = #{year})")
    IPage<Score> selectPageWithStudent(Page<Score> page, @Param("studentName") String studentName, @Param("year") Integer year);
}

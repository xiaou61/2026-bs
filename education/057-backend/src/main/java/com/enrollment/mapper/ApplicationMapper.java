package com.enrollment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Application;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ApplicationMapper extends BaseMapper<Application> {
    @Select("SELECT a.*, s.name as student_name, s.exam_no, m1.name as first_major_name, " +
            "m2.name as second_major_name, p.year as plan_year FROM application a " +
            "LEFT JOIN student s ON a.student_id = s.id " +
            "LEFT JOIN major m1 ON a.first_major_id = m1.id " +
            "LEFT JOIN major m2 ON a.second_major_id = m2.id " +
            "LEFT JOIN enrollment_plan p ON a.plan_id = p.id " +
            "WHERE (#{studentName} IS NULL OR s.name LIKE CONCAT('%', #{studentName}, '%')) " +
            "AND (#{status} IS NULL OR a.status = #{status})")
    IPage<Application> selectPageWithStudent(Page<Application> page, @Param("studentName") String studentName, @Param("status") Integer status);
}

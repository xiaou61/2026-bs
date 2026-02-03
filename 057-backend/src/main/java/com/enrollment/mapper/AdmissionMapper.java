package com.enrollment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Admission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdmissionMapper extends BaseMapper<Admission> {
    @Select("SELECT ad.*, s.name as student_name, s.exam_no, m.name as major_name, p.year as plan_year " +
            "FROM admission ad " +
            "LEFT JOIN student s ON ad.student_id = s.id " +
            "LEFT JOIN major m ON ad.major_id = m.id " +
            "LEFT JOIN enrollment_plan p ON ad.plan_id = p.id " +
            "WHERE (#{studentName} IS NULL OR s.name LIKE CONCAT('%', #{studentName}, '%')) " +
            "AND (#{status} IS NULL OR ad.status = #{status})")
    IPage<Admission> selectPageWithStudent(Page<Admission> page, @Param("studentName") String studentName, @Param("status") Integer status);
}

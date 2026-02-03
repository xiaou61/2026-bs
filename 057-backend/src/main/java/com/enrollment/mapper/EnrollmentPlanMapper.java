package com.enrollment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.EnrollmentPlan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EnrollmentPlanMapper extends BaseMapper<EnrollmentPlan> {
    @Select("SELECT p.*, m.name as major_name, d.name as department_name FROM enrollment_plan p " +
            "LEFT JOIN major m ON p.major_id = m.id " +
            "LEFT JOIN department d ON m.department_id = d.id " +
            "WHERE (#{year} IS NULL OR p.year = #{year}) " +
            "AND (#{majorId} IS NULL OR p.major_id = #{majorId})")
    IPage<EnrollmentPlan> selectPageWithMajor(Page<EnrollmentPlan> page, @Param("year") Integer year, @Param("majorId") Long majorId);
}

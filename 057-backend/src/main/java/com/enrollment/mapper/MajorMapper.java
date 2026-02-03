package com.enrollment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Major;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MajorMapper extends BaseMapper<Major> {
    @Select("SELECT m.*, d.name as department_name FROM major m " +
            "LEFT JOIN department d ON m.department_id = d.id " +
            "WHERE (#{name} IS NULL OR m.name LIKE CONCAT('%', #{name}, '%')) " +
            "AND (#{departmentId} IS NULL OR m.department_id = #{departmentId})")
    IPage<Major> selectPageWithDept(Page<Major> page, @Param("name") String name, @Param("departmentId") Long departmentId);
}

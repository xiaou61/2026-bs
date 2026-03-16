package com.course.mapper;

import com.course.entity.DepartmentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentInfoMapper {
    List<DepartmentInfo> selectList(@Param("name") String name, @Param("status") Integer status);
    List<DepartmentInfo> selectEnabled();
    DepartmentInfo selectById(@Param("id") Long id);
    int insert(DepartmentInfo entity);
    int update(DepartmentInfo entity);
    int deleteById(@Param("id") Long id);
}

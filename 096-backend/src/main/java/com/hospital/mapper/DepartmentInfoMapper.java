package com.hospital.mapper;

import com.hospital.entity.DepartmentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentInfoMapper {
    List<DepartmentInfo> selectPage(@Param("name") String name, @Param("status") Integer status);

    List<DepartmentInfo> selectEnabled();

    DepartmentInfo selectById(@Param("id") Long id);

    void insert(DepartmentInfo entity);

    void update(DepartmentInfo entity);

    void deleteById(@Param("id") Long id);

    long countAll();
}

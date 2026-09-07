package com.course.mapper;

import com.course.entity.MajorInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MajorInfoMapper {
    List<MajorInfo> selectList(@Param("name") String name, @Param("departmentId") Long departmentId, @Param("status") Integer status);
    List<MajorInfo> selectEnabled();
    MajorInfo selectById(@Param("id") Long id);
    int insert(MajorInfo entity);
    int update(MajorInfo entity);
    int deleteById(@Param("id") Long id);
}

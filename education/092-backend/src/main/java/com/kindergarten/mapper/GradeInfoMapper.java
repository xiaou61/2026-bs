package com.kindergarten.mapper;

import com.kindergarten.entity.GradeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradeInfoMapper {
    List<GradeInfo> selectList(@Param("name") String name, @Param("departmentId") Long departmentId, @Param("status") Integer status);
    List<GradeInfo> selectEnabled();
    GradeInfo selectById(@Param("id") Long id);
    int insert(GradeInfo entity);
    int update(GradeInfo entity);
    int deleteById(@Param("id") Long id);
}

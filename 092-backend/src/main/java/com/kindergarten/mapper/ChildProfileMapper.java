package com.kindergarten.mapper;

import com.kindergarten.entity.ChildProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChildProfileMapper {
    List<ChildProfile> selectList(@Param("classId") Long classId, @Param("parentId") Long parentId, @Param("profileStatus") Integer profileStatus, @Param("teacherId") Long teacherId);
    List<ChildProfile> selectByParentId(@Param("parentId") Long parentId);
    ChildProfile selectById(@Param("id") Long id);
    long countAll();
    int insert(ChildProfile entity);
    int update(ChildProfile entity);
    int deleteById(@Param("id") Long id);
}

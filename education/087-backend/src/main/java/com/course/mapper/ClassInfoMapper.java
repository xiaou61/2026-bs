package com.course.mapper;

import com.course.entity.ClassInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassInfoMapper {
    List<ClassInfo> selectList(@Param("name") String name, @Param("majorId") Long majorId, @Param("status") Integer status);
    List<ClassInfo> selectEnabled();
    ClassInfo selectById(@Param("id") Long id);
    int insert(ClassInfo entity);
    int update(ClassInfo entity);
    int deleteById(@Param("id") Long id);
}

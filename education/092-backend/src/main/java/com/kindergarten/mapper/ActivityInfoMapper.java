package com.kindergarten.mapper;

import com.kindergarten.entity.ActivityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityInfoMapper {
    List<ActivityInfo> selectList(@Param("courseName") String courseName, @Param("teacherId") Long teacherId, @Param("termId") Long termId, @Param("status") Integer status);
    ActivityInfo selectById(@Param("id") Long id);
    List<ActivityInfo> selectEnabled();
    long countAll();
    int insert(ActivityInfo entity);
    int update(ActivityInfo entity);
    int deleteById(@Param("id") Long id);
}

package com.kindergarten.mapper;

import com.kindergarten.entity.ParentFeedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParentFeedbackMapper {
    List<ParentFeedback> selectList(@Param("childId") Long childId, @Param("parentId") Long parentId, @Param("teacherId") Long teacherId);
    ParentFeedback selectById(@Param("id") Long id);
    int insert(ParentFeedback entity);
    int update(ParentFeedback entity);
}

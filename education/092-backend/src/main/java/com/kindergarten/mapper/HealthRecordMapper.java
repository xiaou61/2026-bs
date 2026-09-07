package com.kindergarten.mapper;

import com.kindergarten.entity.HealthRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HealthRecordMapper {
    List<HealthRecord> selectList(@Param("childId") Long childId, @Param("parentId") Long parentId, @Param("teacherId") Long teacherId);
    HealthRecord selectById(@Param("id") Long id);
    int insert(HealthRecord entity);
    int update(HealthRecord entity);
}

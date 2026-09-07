package com.course.mapper;

import com.course.entity.ScoreRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreRecordMapper {
    List<ScoreRecord> selectList(@Param("scheduleId") Long scheduleId, @Param("studentId") Long studentId, @Param("teacherId") Long teacherId);
    ScoreRecord selectBySelectionId(@Param("selectionId") Long selectionId);
    int insert(ScoreRecord entity);
    int update(ScoreRecord entity);
}

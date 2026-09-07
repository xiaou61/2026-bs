package com.teachres.mapper;

import com.teachres.entity.EvalRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface EvalRecordMapper {
    EvalRecord selectById(@Param("id") Long id);

    EvalRecord selectByTaskAndStudent(@Param("taskId") Long taskId,
                                      @Param("studentId") Long studentId);

    int insert(EvalRecord record);

    List<Map<String, Object>> selectMyList(@Param("studentId") Long studentId,
                                           @Param("taskId") Long taskId,
                                           @Param("courseId") Long courseId);

    List<Map<String, Object>> selectTaskRecordList(@Param("taskId") Long taskId);

    long countAll();

    BigDecimal avgScoreAll();

    List<Map<String, Object>> selectMonthTrend();

    List<Map<String, Object>> selectCourseTop5();
}

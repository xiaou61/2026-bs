package com.teachres.mapper;

import com.teachres.entity.EvalRecordItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EvalRecordItemMapper {
    int insertBatch(@Param("items") List<EvalRecordItem> items);

    List<Map<String, Object>> selectByRecordId(@Param("recordId") Long recordId);

    List<Map<String, Object>> selectIndicatorAvgByTask(@Param("taskId") Long taskId);
}

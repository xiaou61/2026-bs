package com.opera.mapper;

import com.opera.entity.ReviewRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewRecordMapper {
    List<ReviewRecord> selectList(@Param("scheduleId") Long scheduleId, @Param("memberId") Long memberId, @Param("artistId") Long artistId);
    ReviewRecord selectBySelectionId(@Param("selectionId") Long selectionId);
    int insert(ReviewRecord entity);
    int update(ReviewRecord entity);
}



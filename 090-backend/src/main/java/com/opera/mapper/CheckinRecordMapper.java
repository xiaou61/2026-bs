package com.opera.mapper;

import com.opera.entity.CheckinRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckinRecordMapper {
    List<CheckinRecord> selectList(@Param("scheduleId") Long scheduleId, @Param("memberId") Long memberId, @Param("artistId") Long artistId);
    CheckinRecord selectById(@Param("id") Long id);
    int insert(CheckinRecord entity);
    int update(CheckinRecord entity);
    int deleteById(@Param("id") Long id);
}



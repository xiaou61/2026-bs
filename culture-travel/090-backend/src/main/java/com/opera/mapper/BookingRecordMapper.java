package com.opera.mapper;

import com.opera.entity.BookingRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookingRecordMapper {
    List<BookingRecord> selectList(@Param("scheduleId") Long scheduleId, @Param("memberId") Long memberId, @Param("selectStatus") Integer selectStatus, @Param("artistId") Long artistId);
    BookingRecord selectById(@Param("id") Long id);
    BookingRecord selectByScheduleAndStudent(@Param("scheduleId") Long scheduleId, @Param("memberId") Long memberId);
    long countAll();
    int insert(BookingRecord entity);
    int update(BookingRecord entity);
    int deleteById(@Param("id") Long id);
}



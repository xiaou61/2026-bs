package com.programming.learning.mapper;

import com.programming.learning.entity.CheckInLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 打卡记录Mapper接口
 */
@Mapper
public interface CheckInLogMapper {

    int insert(CheckInLog checkInLog);

    CheckInLog selectByUserIdAndDate(@Param("userId") Long userId, @Param("checkInDate") LocalDate checkInDate);

    List<CheckInLog> selectByUserId(@Param("userId") Long userId, @Param("limit") Integer limit);

    Long countByUserId(Long userId);

    CheckInLog selectLatestByUserId(Long userId);

    List<CheckInLog> selectContinuousRank(@Param("limit") Integer limit);
}

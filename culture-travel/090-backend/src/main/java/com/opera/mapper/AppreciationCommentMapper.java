package com.opera.mapper;

import com.opera.entity.AppreciationComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppreciationCommentMapper {
    List<AppreciationComment> selectList(@Param("scheduleId") Long scheduleId, @Param("memberId") Long memberId, @Param("artistId") Long artistId);
    AppreciationComment selectByScheduleAndStudent(@Param("scheduleId") Long scheduleId, @Param("memberId") Long memberId);
    List<AppreciationComment> selectTeacherResult(@Param("artistId") Long artistId);
    int insert(AppreciationComment entity);
}



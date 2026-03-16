package com.opera.mapper;

import com.opera.entity.MediaResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MediaResourceMapper {
    List<MediaResource> selectList(@Param("scheduleId") Long scheduleId, @Param("artistId") Long artistId, @Param("keyword") String keyword);
    List<MediaResource> selectStudentList(@Param("memberId") Long memberId, @Param("keyword") String keyword);
    MediaResource selectById(@Param("id") Long id);
    int insert(MediaResource entity);
    int update(MediaResource entity);
    int deleteById(@Param("id") Long id);
}



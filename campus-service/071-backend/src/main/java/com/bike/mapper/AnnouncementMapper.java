package com.bike.mapper;

import com.bike.entity.Announcement;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AnnouncementMapper {
    List<Announcement> findList(@Param("title") String title, @Param("type") Integer type);
    Announcement findById(@Param("id") Long id);
    int insert(Announcement announcement);
    int update(Announcement announcement);
    int deleteById(@Param("id") Long id);
}

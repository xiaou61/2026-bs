package com.movie.mapper;

import com.movie.entity.Announcement;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AnnouncementMapper {
    List<Announcement> selectPage(@Param("title") String title);
    List<Announcement> selectLatest();
    Announcement selectById(Long id);
    int insert(Announcement announcement);
    int update(Announcement announcement);
    int deleteById(Long id);
}

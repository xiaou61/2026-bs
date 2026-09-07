package com.hrm.mapper;

import com.hrm.entity.Announcement;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AnnouncementMapper {
    Announcement selectById(Long id);
    List<Announcement> selectList(@Param("title") String title, @Param("status") Integer status);
    List<Announcement> selectTop(int limit);
    int insert(Announcement announcement);
    int update(Announcement announcement);
    int deleteById(Long id);
}

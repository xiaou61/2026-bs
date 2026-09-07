package com.classic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.classic.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {
}

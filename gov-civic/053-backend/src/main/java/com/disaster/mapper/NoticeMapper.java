package com.disaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.disaster.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}

package com.railway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.railway.entity.SystemNotice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper extends BaseMapper<SystemNotice> {
}

package com.xiaou.confession.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.confession.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}


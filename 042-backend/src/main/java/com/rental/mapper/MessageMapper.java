package com.rental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rental.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}

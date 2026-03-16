package com.railway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.railway.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {
}

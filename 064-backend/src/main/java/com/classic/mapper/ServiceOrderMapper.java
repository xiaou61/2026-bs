package com.classic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.classic.entity.ServiceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ServiceOrderMapper extends BaseMapper<ServiceOrder> {

    @Select("select date(create_time) as day, count(1) as total from service_order where create_time between #{start} and #{end} group by date(create_time)")
    List<Map<String, Object>> dailyCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Select("select status, count(1) as total from service_order group by status")
    List<Map<String, Object>> statusStats();
}

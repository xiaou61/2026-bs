package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.RoomType;
import com.xiaou.mapper.RoomTypeMapper;
import com.xiaou.service.RoomTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeMapper, RoomType> implements RoomTypeService {

    @Override
    public List<RoomType> listByHomestay(Long homestayId) {
        LambdaQueryWrapper<RoomType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoomType::getHomestayId, homestayId);
        wrapper.eq(RoomType::getStatus, 1);
        wrapper.orderByAsc(RoomType::getPrice);
        return list(wrapper);
    }
}

package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.RoomType;

import java.util.List;

public interface RoomTypeService extends IService<RoomType> {
    List<RoomType> listByHomestay(Long homestayId);
}

package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Elder;

public interface ElderService extends IService<Elder> {
    IPage<Elder> pageList(Integer current, Integer size, String name, Integer status, Integer careLevel);
    void checkIn(Elder elder);
    void checkOut(Long id);
}

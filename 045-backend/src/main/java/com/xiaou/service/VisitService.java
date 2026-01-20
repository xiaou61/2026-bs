package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Visit;

public interface VisitService extends IService<Visit> {
    void apply(Visit visit, Long userId);
    void approve(Long id, Integer status, String remark, Long approveUserId);
    IPage<Visit> pageList(Long elderId, Integer current, Integer size, Integer status);
    IPage<Visit> pageByUser(Long userId, Integer current, Integer size);
}

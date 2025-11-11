package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.PointsRecord;
import com.xiaou.resource.mapper.PointsRecordMapper;
import org.springframework.stereotype.Service;

@Service
public class PointsRecordService extends ServiceImpl<PointsRecordMapper, PointsRecord> {

    public IPage<PointsRecord> getMyPointsRecords(Long userId, Integer page, Integer size) {
        Page<PointsRecord> pageParam = new Page<>(page, size);
        QueryWrapper<PointsRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }
}


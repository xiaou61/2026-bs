package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.entity.GrowthRecord;
import com.charity.mapper.GrowthRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrowthRecordService {

    @Autowired
    private GrowthRecordMapper growthRecordMapper;

    public Page<GrowthRecord> getList(int pageNum, int pageSize, Long childId) {
        Page<GrowthRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<GrowthRecord> wrapper = new QueryWrapper<>();
        if (childId != null) {
            wrapper.eq("child_id", childId);
        }
        wrapper.orderByDesc("record_date");
        return growthRecordMapper.selectPage(page, wrapper);
    }

    public void add(GrowthRecord growthRecord) {
        growthRecordMapper.insert(growthRecord);
    }
}

package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.GrowthRecord;
import com.charity.mapper.GrowthRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class GrowthRecordService {

    @Autowired
    private GrowthRecordMapper growthRecordMapper;

    @Autowired
    private UserService userService;

    public Page<GrowthRecord> getList(int pageNum, int pageSize, Long childId) {
        Page<GrowthRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<GrowthRecord> wrapper = new QueryWrapper<>();
        if (childId != null) {
            wrapper.eq("child_id", childId);
        }
        wrapper.orderByDesc("record_date");
        return growthRecordMapper.selectPage(page, wrapper);
    }

    public void add(GrowthRecord growthRecord, Long currentUserId) {
        userService.requireAnyRole(currentUserId, "volunteer");
        if (growthRecord.getChildId() == null) {
            throw new BusinessException(400, "儿童ID不能为空");
        }
        if (growthRecord.getRecordDate() == null) {
            growthRecord.setRecordDate(LocalDate.now());
        }
        growthRecord.setRecorderId(currentUserId);
        growthRecordMapper.insert(growthRecord);
    }
}

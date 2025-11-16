package com.xiaou.studyroom.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.studyroom.entity.CreditRecord;
import com.xiaou.studyroom.mapper.CreditRecordMapper;
import com.xiaou.studyroom.service.CreditRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditRecordServiceImpl extends ServiceImpl<CreditRecordMapper, CreditRecord> implements CreditRecordService {

    @Override
    public Page<CreditRecord> getUserCreditRecords(Long userId, int current, int size) {
        Page<CreditRecord> page = new Page<>(current, size);
        QueryWrapper<CreditRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .orderByDesc("create_time");
        return page(page, queryWrapper);
    }

    @Override
    public List<CreditRecord> getRecentCreditRecords(Long userId, int limit) {
        QueryWrapper<CreditRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .orderByDesc("create_time")
                   .last("LIMIT " + limit);
        return list(queryWrapper);
    }

    @Override
    public boolean addCreditRecord(Long userId, Integer scoreChange, String changeReason, String relatedType, Long relatedId) {
        CreditRecord record = new CreditRecord();
        record.setUserId(userId);
        record.setScoreChange(scoreChange);
        record.setChangeReason(changeReason);
        record.setRelatedType(relatedType);
        record.setRelatedId(relatedId);
        return save(record);
    }

    @Override
    public int getTotalCreditChange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        QueryWrapper<CreditRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .between("create_time", startDate, endDate);

        List<CreditRecord> records = list(queryWrapper);
        return records.stream()
                .mapToInt(CreditRecord::getScoreChange)
                .sum();
    }

    @Override
    public Page<CreditRecord> getCreditRecordPage(int current, int size, Long userId, String reason) {
        Page<CreditRecord> page = new Page<>(current, size);
        QueryWrapper<CreditRecord> queryWrapper = new QueryWrapper<>();

        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        }
        if (StrUtil.isNotBlank(reason)) {
            queryWrapper.like("change_reason", reason);
        }

        queryWrapper.orderByDesc("create_time");
        return page(page, queryWrapper);
    }
}
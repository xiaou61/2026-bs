package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.PointsRecord;
import com.xiaou.mapper.PointsRecordMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PointsRecordService extends ServiceImpl<PointsRecordMapper, PointsRecord> {

    public void addRecord(Long userId, String userName, Integer type, Integer points, 
                         Integer balance, Long relatedId, String relatedTitle, String remark) {
        PointsRecord record = new PointsRecord();
        record.setUserId(userId);
        record.setUserName(userName);
        record.setType(type);
        record.setPoints(points);
        record.setBalance(balance);
        record.setRelatedId(relatedId);
        record.setRelatedTitle(relatedTitle);
        record.setRemark(remark);
        record.setCreateTime(LocalDateTime.now());
        this.save(record);
    }

    public List<PointsRecord> getUserRecords(Long userId) {
        return this.list(new QueryWrapper<PointsRecord>()
                .eq("user_id", userId)
                .orderByDesc("create_time"));
    }
}


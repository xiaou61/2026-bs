package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.DepositRecord;
import com.xiaou.campusshare.mapper.DepositRecordMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DepositRecordService extends ServiceImpl<DepositRecordMapper, DepositRecord> {

    public void addRecord(Long userId, Long orderId, String type, BigDecimal amount, BigDecimal before, BigDecimal after, String reason) {
        DepositRecord record = new DepositRecord();
        record.setUserId(userId);
        record.setOrderId(orderId);
        record.setRecordType(type);
        record.setAmount(amount);
        record.setBeforeBalance(before);
        record.setAfterBalance(after);
        record.setReason(reason);
        save(record);
    }
}


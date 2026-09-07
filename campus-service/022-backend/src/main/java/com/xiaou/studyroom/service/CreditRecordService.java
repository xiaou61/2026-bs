package com.xiaou.studyroom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.studyroom.entity.CreditRecord;

import java.util.List;

public interface CreditRecordService extends IService<CreditRecord> {

    Page<CreditRecord> getUserCreditRecords(Long userId, int current, int size);

    List<CreditRecord> getRecentCreditRecords(Long userId, int limit);

    boolean addCreditRecord(Long userId, Integer scoreChange, String changeReason, String relatedType, Long relatedId);

    int getTotalCreditChange(Long userId, java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);

    Page<CreditRecord> getCreditRecordPage(int current, int size, Long userId, String reason);
}
package com.charity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.BusinessException;
import com.charity.entity.FundRecord;
import com.charity.mapper.FundRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class FundRecordService {

    @Autowired
    private FundRecordMapper fundRecordMapper;

    @Autowired
    private UserService userService;

    public Page<FundRecord> getList(int pageNum, int pageSize, String recordType, LocalDate startDate, LocalDate endDate, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        Page<FundRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<FundRecord> wrapper = new QueryWrapper<>();
        if (recordType != null && !recordType.isEmpty()) {
            wrapper.eq("record_type", recordType);
        }
        if (startDate != null) {
            wrapper.ge("record_date", startDate);
        }
        if (endDate != null) {
            wrapper.le("record_date", endDate);
        }
        wrapper.orderByDesc("record_date");
        return fundRecordMapper.selectPage(page, wrapper);
    }

    public void add(FundRecord fundRecord, Long currentUserId) {
        userService.requireAdmin(currentUserId);
        if (fundRecord.getAmount() == null || fundRecord.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(400, "资金金额必须大于0");
        }
        if (fundRecord.getRecordDate() == null) {
            fundRecord.setRecordDate(LocalDate.now());
        }
        fundRecord.setOperatorId(currentUserId);
        fundRecordMapper.insert(fundRecord);
    }
}

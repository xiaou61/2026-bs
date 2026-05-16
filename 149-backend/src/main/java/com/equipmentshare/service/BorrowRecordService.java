package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.BorrowRecord;
import com.equipmentshare.mapper.BorrowRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowRecordService extends BaseCrudService<BorrowRecord> {
    private final BorrowRecordMapper borrowRecordMapper;

    @Override
    protected BaseMapper<BorrowRecord> mapper() {
        return borrowRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"borrow_no", "equipment_name", "borrower_name", "borrow_purpose"};
    }
}









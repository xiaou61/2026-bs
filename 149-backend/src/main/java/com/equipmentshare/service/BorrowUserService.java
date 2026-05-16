package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.BorrowUser;
import com.equipmentshare.mapper.BorrowUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowUserService extends BaseCrudService<BorrowUser> {
    private final BorrowUserMapper borrowUserMapper;

    @Override
    protected BaseMapper<BorrowUser> mapper() {
        return borrowUserMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"borrower_no", "borrower_name", "class_name", "phone_number"};
    }
}









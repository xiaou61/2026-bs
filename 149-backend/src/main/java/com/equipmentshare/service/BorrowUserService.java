package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.BorrowUser;
import com.equipmentshare.mapper.BorrowUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowUserService extends BaseCrudService<BorrowUser> {
    private final BorrowUserMapper stockItemMapper;

    @Override
    protected BaseMapper<BorrowUser> mapper() {
        return stockItemMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "consumable_name", "lab_name", "current_qty"};
    }
}









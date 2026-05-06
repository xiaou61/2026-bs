package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.ReturnConfirmation;
import com.equipmentshare.mapper.ReturnConfirmationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnConfirmationService extends BaseCrudService<ReturnConfirmation> {
    private final ReturnConfirmationMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<ReturnConfirmation> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}









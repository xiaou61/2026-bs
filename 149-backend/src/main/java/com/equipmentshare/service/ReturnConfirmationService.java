package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.ReturnConfirmation;
import com.equipmentshare.mapper.ReturnConfirmationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnConfirmationService extends BaseCrudService<ReturnConfirmation> {
    private final ReturnConfirmationMapper returnConfirmationMapper;

    @Override
    protected BaseMapper<ReturnConfirmation> mapper() {
        return returnConfirmationMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"confirm_no", "equipment_name", "returner_name", "confirm_time"};
    }
}









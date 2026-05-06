package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.DutySchedule;
import com.psychologycare.mapper.DutyScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DutyScheduleService extends BaseCrudService<DutySchedule> {
    private final DutyScheduleMapper stockItemMapper;

    @Override
    protected BaseMapper<DutySchedule> mapper() {
        return stockItemMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "consumable_name", "lab_name", "current_qty"};
    }
}








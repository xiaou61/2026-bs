package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.VolunteerProfile;
import com.timebank.mapper.VolunteerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolunteerProfileService extends BaseCrudService<VolunteerProfile> {
    private final VolunteerProfileMapper stockItemMapper;

    @Override
    protected BaseMapper<VolunteerProfile> mapper() {
        return stockItemMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "consumable_name", "lab_name", "current_qty"};
    }
}






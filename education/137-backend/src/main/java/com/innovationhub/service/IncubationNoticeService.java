package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.IncubationNotice;
import com.innovationhub.mapper.IncubationNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncubationNoticeService extends BaseCrudService<IncubationNotice> {
    private final IncubationNoticeMapper stockWarningMapper;

    @Override
    protected BaseMapper<IncubationNotice> mapper() {
        return stockWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "consumable_name", "current_qty", "warning_level"};
    }
}



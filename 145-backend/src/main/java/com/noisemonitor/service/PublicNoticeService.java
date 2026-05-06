package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.PublicNotice;
import com.noisemonitor.mapper.PublicNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicNoticeService extends BaseCrudService<PublicNotice> {
    private final PublicNoticeMapper stockWarningMapper;

    @Override
    protected BaseMapper<PublicNotice> mapper() {
        return stockWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "consumable_name", "current_qty", "warning_level"};
    }
}







package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.WarningNotice;
import com.assetrfid.mapper.WarningNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarningNoticeService extends BaseCrudService<WarningNotice> {
    private final WarningNoticeMapper stockWarningMapper;

    @Override
    protected BaseMapper<WarningNotice> mapper() {
        return stockWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "consumable_name", "current_qty", "warning_level"};
    }
}





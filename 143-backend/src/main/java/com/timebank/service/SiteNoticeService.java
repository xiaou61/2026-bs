package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.SiteNotice;
import com.timebank.mapper.SiteNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteNoticeService extends BaseCrudService<SiteNotice> {
    private final SiteNoticeMapper stockWarningMapper;

    @Override
    protected BaseMapper<SiteNotice> mapper() {
        return stockWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "consumable_name", "current_qty", "warning_level"};
    }
}






package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.SystemNotice;
import com.psychologycare.mapper.SystemNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemNoticeService extends BaseCrudService<SystemNotice> {
    private final SystemNoticeMapper stockWarningMapper;

    @Override
    protected BaseMapper<SystemNotice> mapper() {
        return stockWarningMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"warning_no", "consumable_name", "current_qty", "warning_level"};
    }
}








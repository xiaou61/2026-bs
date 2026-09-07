package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.WarningNotice;
import com.assetrfid.mapper.WarningNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarningNoticeService extends BaseCrudService<WarningNotice> {
    private final WarningNoticeMapper warningNoticeMapper;

    @Override
    protected BaseMapper<WarningNotice> mapper() {
        return warningNoticeMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"notice_no", "asset_name", "warning_type", "handler_name"};
    }
}

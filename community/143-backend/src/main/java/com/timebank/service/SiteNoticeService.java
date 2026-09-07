package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.SiteNotice;
import com.timebank.mapper.SiteNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteNoticeService extends BaseCrudService<SiteNotice> {
    private final SiteNoticeMapper siteNoticeMapper;

    @Override
    protected BaseMapper<SiteNotice> mapper() {
        return siteNoticeMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"notice_no", "project_name", "notice_type", "publish_time"};
    }
}


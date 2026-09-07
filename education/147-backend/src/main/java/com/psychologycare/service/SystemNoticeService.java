package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.SystemNotice;
import com.psychologycare.mapper.SystemNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemNoticeService extends BaseCrudService<SystemNotice> {
    private final SystemNoticeMapper systemNoticeMapper;

    @Override
    protected BaseMapper<SystemNotice> mapper() {
        return systemNoticeMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"notice_no", "case_theme", "notice_type", "notice_content"};
    }
}








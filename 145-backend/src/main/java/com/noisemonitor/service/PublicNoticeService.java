package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.PublicNotice;
import com.noisemonitor.mapper.PublicNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicNoticeService extends BaseCrudService<PublicNotice> {
    private final PublicNoticeMapper publicNoticeMapper;

    @Override
    protected BaseMapper<PublicNotice> mapper() {
        return publicNoticeMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"notice_no", "complaint_title", "notice_type", "notice_content"};
    }
}







package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.RectificationNotice;
import com.noisemonitor.mapper.RectificationNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RectificationNoticeService extends BaseCrudService<RectificationNotice> {
    private final RectificationNoticeMapper rectificationNoticeMapper;

    @Override
    protected BaseMapper<RectificationNotice> mapper() {
        return rectificationNoticeMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"notice_no", "complaint_title", "rectify_requirement", "responsible_unit"};
    }
}







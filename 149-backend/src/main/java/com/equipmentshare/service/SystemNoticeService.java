package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.SystemNotice;
import com.equipmentshare.mapper.SystemNoticeMapper;
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
        return new String[]{"notice_no", "equipment_name", "notice_type", "notice_content"};
    }
}









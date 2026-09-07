package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.CounselRecord;
import com.psychologycare.mapper.CounselRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselRecordService extends BaseCrudService<CounselRecord> {
    private final CounselRecordMapper counselRecordMapper;

    @Override
    protected BaseMapper<CounselRecord> mapper() {
        return counselRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "case_theme", "counselor_name", "conclusion_summary"};
    }
}








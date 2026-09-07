package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.MailSendRecord;
import com.phishingtraining.mapper.MailSendRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendRecordService extends BaseCrudService<MailSendRecord> {
    private final MailSendRecordMapper mapper;

    @Override
    protected BaseMapper<MailSendRecord> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"record_no", "campaign_name", "employee_name", "email"};
    }

}

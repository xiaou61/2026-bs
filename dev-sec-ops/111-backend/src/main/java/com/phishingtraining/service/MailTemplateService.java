package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.MailTemplate;
import com.phishingtraining.mapper.MailTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailTemplateService extends BaseCrudService<MailTemplate> {
    private final MailTemplateMapper mapper;

    @Override
    protected BaseMapper<MailTemplate> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"template_name", "subject_text", "bait_type", "sender_name"};
    }

}

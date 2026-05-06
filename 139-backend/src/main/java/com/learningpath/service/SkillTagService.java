package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.SkillTag;
import com.learningpath.mapper.SkillTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillTagService extends BaseCrudService<SkillTag> {
    private final SkillTagMapper outboundRecordMapper;

    @Override
    protected BaseMapper<SkillTag> mapper() {
        return outboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"outbound_no", "consumable_name", "lab_name", "outbound_qty"};
    }
}




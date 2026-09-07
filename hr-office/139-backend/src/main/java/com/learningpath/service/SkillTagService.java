package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.SkillTag;
import com.learningpath.mapper.SkillTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillTagService extends BaseCrudService<SkillTag> {
    private final SkillTagMapper skillTagMapper;

    @Override
    protected BaseMapper<SkillTag> mapper() {
        return skillTagMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"tag_no", "tag_name", "category_name", "owner_name"};
    }
}




package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.LearnerProfile;
import com.learningpath.mapper.LearnerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearnerProfileService extends BaseCrudService<LearnerProfile> {
    private final LearnerProfileMapper labRoomMapper;

    @Override
    protected BaseMapper<LearnerProfile> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}




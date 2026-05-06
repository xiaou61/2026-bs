package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.TrainingProgram;
import com.learningpath.mapper.TrainingProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingProgramService extends BaseCrudService<TrainingProgram> {
    private final TrainingProgramMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<TrainingProgram> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}




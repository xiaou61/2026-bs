package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.TrainingProgram;
import com.learningpath.mapper.TrainingProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingProgramService extends BaseCrudService<TrainingProgram> {
    private final TrainingProgramMapper trainingProgramMapper;

    @Override
    protected BaseMapper<TrainingProgram> mapper() {
        return trainingProgramMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"program_no", "program_name", "target_role", "organizer_name"};
    }
}




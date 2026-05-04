package com.phishingtraining.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.ExamQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {
}

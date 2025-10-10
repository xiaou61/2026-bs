package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Question;
import com.xiaou.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question> {

    public List<Question> getQuestionsBySurveyId(Long surveyId) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getSurveyId, surveyId);
        wrapper.orderByAsc(Question::getOrderNo);
        return list(wrapper);
    }

    public void addQuestion(Question question) {
        save(question);
    }

    public void deleteQuestion(Long id) {
        removeById(id);
    }
}


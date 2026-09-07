package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Answer;
import com.xiaou.entity.AnswerDetail;
import com.xiaou.entity.Question;
import com.xiaou.mapper.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AnswerService extends ServiceImpl<AnswerMapper, Answer> {

    @Autowired
    private AnswerDetailService answerDetailService;

    @Autowired
    private QuestionService questionService;

    @Transactional
    public void submitAnswer(Long surveyId, String userIp, List<AnswerDetail> details) {
        Answer answer = new Answer();
        answer.setSurveyId(surveyId);
        answer.setUserIp(userIp);
        save(answer);

        for (AnswerDetail detail : details) {
            detail.setAnswerId(answer.getId());
            answerDetailService.save(detail);
        }
    }

    public Map<String, Object> getStatistics(Long surveyId) {
        List<Question> questions = questionService.getQuestionsBySurveyId(surveyId);
        
        LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Answer::getSurveyId, surveyId);
        long totalAnswers = count(wrapper);

        List<Map<String, Object>> questionStats = new ArrayList<>();
        
        for (Question question : questions) {
            Map<String, Object> stat = new HashMap<>();
            stat.put("question", question);

            LambdaQueryWrapper<Answer> answerWrapper = new LambdaQueryWrapper<>();
            answerWrapper.eq(Answer::getSurveyId, surveyId);
            List<Answer> answers = list(answerWrapper);

            List<String> values = new ArrayList<>();
            for (Answer answer : answers) {
                LambdaQueryWrapper<AnswerDetail> detailWrapper = new LambdaQueryWrapper<>();
                detailWrapper.eq(AnswerDetail::getAnswerId, answer.getId());
                detailWrapper.eq(AnswerDetail::getQuestionId, question.getId());
                AnswerDetail detail = answerDetailService.getOne(detailWrapper);
                if (detail != null && detail.getValue() != null) {
                    values.add(detail.getValue());
                }
            }

            if (question.getType().equals("radio") || question.getType().equals("checkbox") || question.getType().equals("select")) {
                Map<String, Integer> optionCount = new HashMap<>();
                for (String value : values) {
                    if (question.getType().equals("checkbox")) {
                        String[] options = value.split(",");
                        for (String option : options) {
                            optionCount.put(option.trim(), optionCount.getOrDefault(option.trim(), 0) + 1);
                        }
                    } else {
                        optionCount.put(value, optionCount.getOrDefault(value, 0) + 1);
                    }
                }
                stat.put("optionCount", optionCount);
            } else {
                stat.put("answers", values);
            }

            questionStats.add(stat);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalAnswers", totalAnswers);
        result.put("questionStats", questionStats);
        return result;
    }
}


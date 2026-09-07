package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Survey;
import com.xiaou.mapper.SurveyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService extends ServiceImpl<SurveyMapper, Survey> {

    public List<Survey> getMySurveys(Long userId) {
        LambdaQueryWrapper<Survey> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Survey::getCreatorId, userId);
        wrapper.orderByDesc(Survey::getCreateTime);
        return list(wrapper);
    }

    public void createSurvey(Survey survey, Integer userRole) {
        if (userRole != 0) {
            throw new RuntimeException("只有管理员可以创建问卷");
        }
        if (survey.getStatus() == null) {
            survey.setStatus(0);
        }
        save(survey);
    }

    public void publishSurvey(Long id, Long userId, Integer userRole) {
        if (userRole != 0) {
            throw new RuntimeException("只有管理员可以发布问卷");
        }
        Survey survey = getById(id);
        if (survey == null) {
            throw new RuntimeException("问卷不存在");
        }
        if (!survey.getCreatorId().equals(userId)) {
            throw new RuntimeException("无权限操作");
        }
        survey.setStatus(1);
        updateById(survey);
    }

    public void deleteSurvey(Long id, Long userId) {
        Survey survey = getById(id);
        if (survey == null) {
            throw new RuntimeException("问卷不存在");
        }
        if (!survey.getCreatorId().equals(userId)) {
            throw new RuntimeException("无权限操作");
        }
        removeById(id);
    }

    public void updateSurvey(Survey survey, Long userId) {
        Survey oldSurvey = getById(survey.getId());
        if (oldSurvey == null) {
            throw new RuntimeException("问卷不存在");
        }
        if (!oldSurvey.getCreatorId().equals(userId)) {
            throw new RuntimeException("无权限操作");
        }
        updateById(survey);
    }
}


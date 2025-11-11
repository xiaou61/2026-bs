package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.Answer;
import com.xiaou.resource.entity.PointsRecord;
import com.xiaou.resource.entity.QuestionAnswer;
import com.xiaou.resource.mapper.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService extends ServiceImpl<AnswerMapper, Answer> {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PointsRecordService pointsRecordService;

    public boolean addAnswer(Answer answer, Long userId) {
        answer.setUserId(userId);
        answer.setLikeCount(0);
        answer.setIsAccepted(0);
        answer.setCreateTime(LocalDateTime.now());
        answer.setUpdateTime(LocalDateTime.now());
        return this.save(answer);
    }

    public List<Answer> getAnswersByQuestionId(Long questionAnswerId) {
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("question_answer_id", questionAnswerId);
        wrapper.orderByDesc("like_count");
        return this.list(wrapper);
    }

    public boolean acceptAnswer(Long answerId, Long askerId) {
        Answer answer = this.getById(answerId);
        if (answer == null) {
            return false;
        }

        QuestionAnswer question = questionAnswerService.getById(answer.getQuestionAnswerId());
        if (question == null || !question.getAskerId().equals(askerId)) {
            return false;
        }

        answer.setIsAccepted(1);
        answer.setUpdateTime(LocalDateTime.now());
        this.updateById(answer);

        question.setStatus("resolved");
        question.setUpdateTime(LocalDateTime.now());
        questionAnswerService.updateById(question);

        if (question.getBounty() > 0) {
            userService.addPoints(answer.getUserId(), question.getBounty());

            PointsRecord record = new PointsRecord();
            record.setUserId(answer.getUserId());
            record.setPoints(question.getBounty());
            record.setType("bounty_reward");
            record.setDescription("获得悬赏奖励：" + question.getTitle());
            record.setCreateTime(LocalDateTime.now());
            pointsRecordService.save(record);
        }

        return true;
    }

    public boolean likeAnswer(Long answerId) {
        Answer answer = this.getById(answerId);
        if (answer != null) {
            answer.setLikeCount(answer.getLikeCount() + 1);
            answer.setUpdateTime(LocalDateTime.now());
            return this.updateById(answer);
        }
        return false;
    }
}


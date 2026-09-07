package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.Answer;
import com.xiaou.resource.entity.PointsRecord;
import com.xiaou.resource.entity.QuestionAnswer;
import com.xiaou.resource.mapper.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService extends ServiceImpl<AnswerMapper, Answer> {
    private static final int ANSWER_REWARD_POINTS = 10;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PointsRecordService pointsRecordService;

    @Transactional(rollbackFor = Exception.class)
    public boolean addAnswer(Answer answer, Long userId) {
        QuestionAnswer question = questionAnswerService.getById(answer.getQuestionAnswerId());
        if (question == null || "resolved".equals(question.getStatus()) || question.getAskerId().equals(userId)) {
            return false;
        }

        QueryWrapper<Answer> duplicateWrapper = new QueryWrapper<>();
        duplicateWrapper.eq("question_answer_id", answer.getQuestionAnswerId()).eq("user_id", userId);
        if (this.count(duplicateWrapper) > 0) {
            return false;
        }

        answer.setUserId(userId);
        answer.setLikeCount(0);
        answer.setIsAccepted(0);
        answer.setCreateTime(LocalDateTime.now());
        answer.setUpdateTime(LocalDateTime.now());
        if (!this.save(answer)) {
            return false;
        }

        if (!userService.addPoints(userId, ANSWER_REWARD_POINTS)) {
            throw new IllegalStateException("回答奖励积分发放失败");
        }

        PointsRecord record = new PointsRecord();
        record.setUserId(userId);
        record.setPoints(ANSWER_REWARD_POINTS);
        record.setType("answer_reward");
        record.setDescription("回答问题奖励：" + question.getTitle());
        record.setCreateTime(LocalDateTime.now());
        if (!pointsRecordService.save(record)) {
            throw new IllegalStateException("保存回答奖励积分记录失败");
        }
        return true;
    }

    public List<Answer> getAnswersByQuestionId(Long questionAnswerId) {
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("question_answer_id", questionAnswerId);
        wrapper.orderByDesc("like_count");
        return this.list(wrapper);
    }

    public boolean acceptAnswer(Long answerId, Long askerId) {
        Answer answer = this.getById(answerId);
        if (answer == null || answer.getIsAccepted() == 1) {
            return false;
        }

        QuestionAnswer question = questionAnswerService.getById(answer.getQuestionAnswerId());
        if (question == null || !question.getAskerId().equals(askerId) || "resolved".equals(question.getStatus())) {
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
            if (!pointsRecordService.save(record)) {
                throw new IllegalStateException("保存悬赏奖励积分记录失败");
            }
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


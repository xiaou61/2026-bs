package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.PointsRecord;
import com.xiaou.resource.entity.QuestionAnswer;
import com.xiaou.resource.mapper.QuestionAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class QuestionAnswerService extends ServiceImpl<QuestionAnswerMapper, QuestionAnswer> {

    @Autowired
    private UserService userService;

    @Autowired
    private PointsRecordService pointsRecordService;

    @Transactional(rollbackFor = Exception.class)
    public boolean askQuestion(QuestionAnswer questionAnswer, Long userId) {
        questionAnswer.setAskerId(userId);
        questionAnswer.setBounty(questionAnswer.getBounty() == null ? 0 : questionAnswer.getBounty());
        questionAnswer.setStatus("pending");
        questionAnswer.setCreateTime(LocalDateTime.now());
        questionAnswer.setUpdateTime(LocalDateTime.now());
        
        if (questionAnswer.getBounty() > 0) {
            boolean deducted = userService.deductPoints(userId, questionAnswer.getBounty());
            if (!deducted) {
                return false;
            }

            PointsRecord record = new PointsRecord();
            record.setUserId(userId);
            record.setPoints(-questionAnswer.getBounty());
            record.setType("bounty");
            record.setDescription("悬赏提问：" + questionAnswer.getTitle());
            record.setCreateTime(LocalDateTime.now());
            if (!pointsRecordService.save(record)) {
                throw new IllegalStateException("保存悬赏积分记录失败");
            }
        }
        
        return this.save(questionAnswer);
    }

    public IPage<QuestionAnswer> getQuestionList(Integer page, Integer size, String category, String status) {
        Page<QuestionAnswer> pageParam = new Page<>(page, size);
        QueryWrapper<QuestionAnswer> wrapper = new QueryWrapper<>();
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }

    public QuestionAnswer getQuestionDetail(Long id) {
        return this.getById(id);
    }

    public IPage<QuestionAnswer> getMyQuestions(Long userId, Integer page, Integer size) {
        Page<QuestionAnswer> pageParam = new Page<>(page, size);
        QueryWrapper<QuestionAnswer> wrapper = new QueryWrapper<>();
        wrapper.eq("asker_id", userId);
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }
}


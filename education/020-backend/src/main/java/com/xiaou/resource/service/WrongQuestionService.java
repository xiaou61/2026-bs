package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.WrongQuestion;
import com.xiaou.resource.mapper.WrongQuestionMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WrongQuestionService extends ServiceImpl<WrongQuestionMapper, WrongQuestion> {

    public boolean addWrongQuestion(Long userId, Long questionId, String userAnswer) {
        QueryWrapper<WrongQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("question_id", questionId);
        WrongQuestion wrongQuestion = this.getOne(wrapper);

        if (wrongQuestion != null) {
            wrongQuestion.setWrongCount(wrongQuestion.getWrongCount() + 1);
            wrongQuestion.setUserAnswer(userAnswer);
            wrongQuestion.setUpdateTime(LocalDateTime.now());
            return this.updateById(wrongQuestion);
        } else {
            wrongQuestion = new WrongQuestion();
            wrongQuestion.setUserId(userId);
            wrongQuestion.setQuestionId(questionId);
            wrongQuestion.setUserAnswer(userAnswer);
            wrongQuestion.setWrongCount(1);
            wrongQuestion.setCreateTime(LocalDateTime.now());
            wrongQuestion.setUpdateTime(LocalDateTime.now());
            return this.save(wrongQuestion);
        }
    }

    public IPage<WrongQuestion> getMyWrongQuestions(Long userId, Integer page, Integer size) {
        Page<WrongQuestion> pageParam = new Page<>(page, size);
        QueryWrapper<WrongQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("update_time");
        return this.page(pageParam, wrapper);
    }

    public boolean removeWrongQuestion(Long id, Long userId) {
        QueryWrapper<WrongQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("user_id", userId);
        WrongQuestion wrongQuestion = this.getOne(wrapper);
        if (wrongQuestion != null) {
            return this.removeById(id);
        }
        return false;
    }
}


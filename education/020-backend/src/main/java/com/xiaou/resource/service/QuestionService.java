package com.xiaou.resource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.Question;
import com.xiaou.resource.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question> {

    public boolean addQuestion(Question question, Long userId) {
        question.setUserId(userId);
        question.setCreateTime(LocalDateTime.now());
        question.setUpdateTime(LocalDateTime.now());
        return this.save(question);
    }

    public IPage<Question> getQuestionList(Integer page, Integer size, String subject, String difficulty, String type) {
        Page<Question> pageParam = new Page<>(page, size);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (subject != null && !subject.isEmpty()) {
            wrapper.eq("subject", subject);
        }
        if (difficulty != null && !difficulty.isEmpty()) {
            wrapper.eq("difficulty", difficulty);
        }
        if (type != null && !type.isEmpty()) {
            wrapper.eq("type", type);
        }
        wrapper.orderByDesc("create_time");
        return this.page(pageParam, wrapper);
    }

    public Question getQuestionDetail(Long id) {
        return this.getById(id);
    }

    public List<Question> generatePaper(String subject, Integer count) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (subject != null && !subject.isEmpty()) {
            wrapper.eq("subject", subject);
        }
        List<Question> allQuestions = this.list(wrapper);
        Collections.shuffle(allQuestions);
        
        if (allQuestions.size() <= count) {
            return allQuestions;
        }
        return allQuestions.subList(0, count);
    }

    public List<Question> getRandomQuestions(String subject, String difficulty, Integer count) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (subject != null && !subject.isEmpty()) {
            wrapper.eq("subject", subject);
        }
        if (difficulty != null && !difficulty.isEmpty()) {
            wrapper.eq("difficulty", difficulty);
        }
        wrapper.last("ORDER BY RAND() LIMIT " + count);
        return this.list(wrapper);
    }
}


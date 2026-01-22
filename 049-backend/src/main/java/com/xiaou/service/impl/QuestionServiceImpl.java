package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.dto.AnswerDTO;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    private final QuestionCategoryMapper categoryMapper;
    private final SubjectMapper subjectMapper;
    private final AnswerRecordMapper answerRecordMapper;
    private final WrongQuestionMapper wrongQuestionMapper;
    private final UserMapper userMapper;

    @Override
    public Page<Question> getPage(int current, int size, Long subjectId, Long categoryId, Integer type, Integer difficulty) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        if (subjectId != null) wrapper.eq(Question::getSubjectId, subjectId);
        if (categoryId != null) wrapper.eq(Question::getCategoryId, categoryId);
        if (type != null) wrapper.eq(Question::getType, type);
        if (difficulty != null) wrapper.eq(Question::getDifficulty, difficulty);
        wrapper.orderByDesc(Question::getCreateTime);
        Page<Question> page = page(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillInfo);
        return page;
    }

    @Override
    public Question getDetail(Long id) {
        Question q = getById(id);
        if (q != null) {
            fillInfo(q);
            q.setViewCount(q.getViewCount() + 1);
            updateById(q);
        }
        return q;
    }

    @Override
    public List<Question> getRandom(Long subjectId, Integer count) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        if (subjectId != null) wrapper.eq(Question::getSubjectId, subjectId);
        wrapper.last("ORDER BY RAND() LIMIT " + count);
        List<Question> list = list(wrapper);
        list.forEach(this::fillInfo);
        return list;
    }

    @Override
    public Map<String, Object> submitAnswer(Long userId, AnswerDTO dto) {
        Question question = getById(dto.getQuestionId());
        boolean isCorrect = question.getAnswer().equals(dto.getUserAnswer());
        
        // 保存答题记录
        AnswerRecord record = new AnswerRecord();
        record.setUserId(userId);
        record.setQuestionId(dto.getQuestionId());
        record.setUserAnswer(dto.getUserAnswer());
        record.setIsCorrect(isCorrect ? 1 : 0);
        record.setTimeSpent(dto.getTimeSpent());
        answerRecordMapper.insert(record);
        
        // 错题处理
        if (!isCorrect) {
            question.setErrorCount(question.getErrorCount() + 1);
            updateById(question);
            
            WrongQuestion wq = wrongQuestionMapper.selectOne(new LambdaQueryWrapper<WrongQuestion>()
                    .eq(WrongQuestion::getUserId, userId).eq(WrongQuestion::getQuestionId, dto.getQuestionId()));
            if (wq == null) {
                wq = new WrongQuestion();
                wq.setUserId(userId);
                wq.setQuestionId(dto.getQuestionId());
                wq.setWrongCount(1);
                wq.setStatus(0);
                wrongQuestionMapper.insert(wq);
            } else {
                wq.setWrongCount(wq.getWrongCount() + 1);
                wq.setLastWrongTime(LocalDateTime.now());
                wq.setStatus(0);
                wrongQuestionMapper.updateById(wq);
            }
        }
        
        // 更新积分
        User user = userMapper.selectById(userId);
        user.setPoints(user.getPoints() + (isCorrect ? 2 : 1));
        userMapper.updateById(user);
        
        Map<String, Object> result = new HashMap<>();
        result.put("isCorrect", isCorrect);
        result.put("correctAnswer", question.getAnswer());
        result.put("analysis", question.getAnalysis());
        return result;
    }

    @Override
    public Page<WrongQuestion> getWrongQuestions(Long userId, int current, int size) {
        Page<WrongQuestion> page = wrongQuestionMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<WrongQuestion>()
                        .eq(WrongQuestion::getUserId, userId)
                        .eq(WrongQuestion::getStatus, 0)
                        .orderByDesc(WrongQuestion::getLastWrongTime));
        page.getRecords().forEach(wq -> wq.setQuestion(getById(wq.getQuestionId())));
        return page;
    }

    @Override
    public void markMastered(Long userId, Long questionId) {
        WrongQuestion wq = wrongQuestionMapper.selectOne(new LambdaQueryWrapper<WrongQuestion>()
                .eq(WrongQuestion::getUserId, userId).eq(WrongQuestion::getQuestionId, questionId));
        if (wq != null) {
            wq.setStatus(1);
            wrongQuestionMapper.updateById(wq);
        }
    }

    private void fillInfo(Question q) {
        if (q.getCategoryId() != null) {
            QuestionCategory c = categoryMapper.selectById(q.getCategoryId());
            if (c != null) q.setCategoryName(c.getName());
        }
        if (q.getSubjectId() != null) {
            Subject s = subjectMapper.selectById(q.getSubjectId());
            if (s != null) q.setSubjectName(s.getName());
        }
    }
}

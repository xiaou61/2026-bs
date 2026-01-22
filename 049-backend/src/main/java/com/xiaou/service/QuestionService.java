package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.AnswerDTO;
import com.xiaou.entity.Question;
import com.xiaou.entity.WrongQuestion;
import java.util.List;
import java.util.Map;

public interface QuestionService extends IService<Question> {
    Page<Question> getPage(int current, int size, Long subjectId, Long categoryId, Integer type, Integer difficulty);
    Question getDetail(Long id);
    List<Question> getRandom(Long subjectId, Integer count);
    Map<String, Object> submitAnswer(Long userId, AnswerDTO dto);
    Page<WrongQuestion> getWrongQuestions(Long userId, int current, int size);
    void markMastered(Long userId, Long questionId);
}

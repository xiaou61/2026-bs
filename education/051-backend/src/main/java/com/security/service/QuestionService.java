package com.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.security.dto.AnswerDTO;
import com.security.entity.Question;
import com.security.vo.AnswerResultVO;
import com.security.vo.QuestionVO;
import com.security.vo.RankVO;

import java.util.List;

public interface QuestionService extends IService<Question> {
    List<QuestionVO> getDailyQuestions(Long userId);
    List<QuestionVO> getCategoryQuestions(Long categoryId, Long userId);
    AnswerResultVO submitAnswer(AnswerDTO dto, Long userId);
    Page<QuestionVO> getWrongQuestions(Long userId, Integer page, Integer size);
    List<RankVO> getRankList();
}

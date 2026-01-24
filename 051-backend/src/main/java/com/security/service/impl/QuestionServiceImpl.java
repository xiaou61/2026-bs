package com.security.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.common.BusinessException;
import com.security.dto.AnswerDTO;
import com.security.entity.AnswerRecord;
import com.security.entity.KnowledgeCategory;
import com.security.entity.Question;
import com.security.mapper.AnswerRecordMapper;
import com.security.mapper.CategoryMapper;
import com.security.mapper.QuestionMapper;
import com.security.service.QuestionService;
import com.security.service.UserService;
import com.security.vo.AnswerResultVO;
import com.security.vo.QuestionVO;
import com.security.vo.RankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private AnswerRecordMapper answerRecordMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<QuestionVO> getDailyQuestions(Long userId) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = "daily:questions:" + today;

        @SuppressWarnings("unchecked")
        List<Long> questionIds = (List<Long>) redisTemplate.opsForValue().get(key);

        if (questionIds == null) {
            List<Question> allQuestions = this.list();
            Collections.shuffle(allQuestions);
            questionIds = allQuestions.stream()
                    .limit(5)
                    .map(Question::getId)
                    .collect(Collectors.toList());
            redisTemplate.opsForValue().set(key, questionIds, 24, TimeUnit.HOURS);
        }

        List<QuestionVO> result = new ArrayList<>();
        for (Long qid : questionIds) {
            Question question = this.getById(qid);
            if (question != null) {
                result.add(convertToVO(question));
            }
        }
        return result;
    }

    @Override
    public List<QuestionVO> getCategoryQuestions(Long categoryId, Long userId) {
        List<Question> questions = this.list(
                new LambdaQueryWrapper<Question>()
                        .eq(Question::getCategoryId, categoryId));
        Collections.shuffle(questions);
        return questions.stream()
                .limit(10)
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public AnswerResultVO submitAnswer(AnswerDTO dto, Long userId) {
        Question question = this.getById(dto.getQuestionId());
        if (question == null) {
            throw new BusinessException("题目不存在");
        }

        boolean isCorrect = question.getAnswer().equals(dto.getAnswer());

        AnswerRecord record = new AnswerRecord();
        record.setUserId(userId);
        record.setQuestionId(dto.getQuestionId());
        record.setUserAnswer(dto.getAnswer());
        record.setIsCorrect(isCorrect ? 1 : 0);
        answerRecordMapper.insert(record);

        int points = 0;
        if (isCorrect) {
            points = question.getDifficulty() * 5;
            userService.addPoints(userId, points);
        }

        AnswerResultVO vo = new AnswerResultVO();
        vo.setIsCorrect(isCorrect);
        vo.setCorrectAnswer(question.getAnswer());
        vo.setExplanation(question.getExplanation());
        vo.setPoints(points);
        return vo;
    }

    @Override
    public Page<QuestionVO> getWrongQuestions(Long userId, Integer page, Integer size) {
        Page<AnswerRecord> recordPage = new Page<>(page, size);
        answerRecordMapper.selectPage(recordPage,
                new LambdaQueryWrapper<AnswerRecord>()
                        .eq(AnswerRecord::getUserId, userId)
                        .eq(AnswerRecord::getIsCorrect, 0)
                        .orderByDesc(AnswerRecord::getCreateTime));

        Page<QuestionVO> voPage = new Page<>(page, size, recordPage.getTotal());
        List<QuestionVO> voList = recordPage.getRecords().stream()
                .map(record -> {
                    Question question = this.getById(record.getQuestionId());
                    return question != null ? convertToVO(question) : null;
                })
                .filter(vo -> vo != null)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public List<RankVO> getRankList() {
        List<Map<String, Object>> rankData = answerRecordMapper.getRankList(50);
        List<RankVO> result = new ArrayList<>();
        int rank = 1;
        for (Map<String, Object> data : rankData) {
            RankVO vo = new RankVO();
            vo.setRank(rank++);
            vo.setUserId(((Number) data.get("id")).longValue());
            vo.setNickname((String) data.get("nickname"));
            vo.setAvatar((String) data.get("avatar"));
            vo.setPoints(((Number) data.get("points")).intValue());
            result.add(vo);
        }
        return result;
    }

    private QuestionVO convertToVO(Question question) {
        QuestionVO vo = new QuestionVO();
        vo.setId(question.getId());
        vo.setCategoryId(question.getCategoryId());
        vo.setContent(question.getContent());
        vo.setOptions(JSONUtil.toList(question.getOptions(), String.class));
        vo.setDifficulty(question.getDifficulty());

        KnowledgeCategory category = categoryMapper.selectById(question.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
        }
        return vo;
    }
}

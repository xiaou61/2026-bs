package com.gongkao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.entity.Question;
import com.gongkao.entity.QuestionBank;
import com.gongkao.mapper.QuestionBankMapper;
import com.gongkao.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionBankMapper questionBankMapper;

    public Page<Question> getList(int pageNum, int pageSize, Long bankId, Long subjectId, String type, String stem, Integer status) {
        Page<Question> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (bankId != null) {
            wrapper.eq("bank_id", bankId);
        }
        if (subjectId != null) {
            wrapper.eq("subject_id", subjectId);
        }
        if (StringUtils.hasText(type)) {
            wrapper.eq("type", type);
        }
        if (StringUtils.hasText(stem)) {
            wrapper.like("stem", stem);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return questionMapper.selectPage(page, wrapper);
    }

    public void add(Question question) {
        if (question.getStatus() == null) {
            question.setStatus(1);
        }
        if (question.getScore() == null) {
            question.setScore(BigDecimal.ONE);
        }
        questionMapper.insert(question);
        updateBankCount(question.getBankId());
    }

    public void update(Question question) {
        Question old = questionMapper.selectById(question.getId());
        questionMapper.updateById(question);
        updateBankCount(question.getBankId());
        if (old != null && old.getBankId() != null && !old.getBankId().equals(question.getBankId())) {
            updateBankCount(old.getBankId());
        }
    }

    public void delete(Long id) {
        Question old = questionMapper.selectById(id);
        questionMapper.deleteById(id);
        if (old != null) {
            updateBankCount(old.getBankId());
        }
    }

    private void updateBankCount(Long bankId) {
        if (bankId == null) {
            return;
        }
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("bank_id", bankId);
        int count = questionMapper.selectCount(wrapper).intValue();
        QuestionBank bank = new QuestionBank();
        bank.setId(bankId);
        bank.setTotalCount(count);
        questionBankMapper.updateById(bank);
    }
}

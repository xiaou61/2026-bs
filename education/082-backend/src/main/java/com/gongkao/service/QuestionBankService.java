package com.gongkao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.entity.QuestionBank;
import com.gongkao.mapper.QuestionBankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class QuestionBankService {

    @Autowired
    private QuestionBankMapper questionBankMapper;

    public Page<QuestionBank> getList(int pageNum, int pageSize, String name, Long subjectId, String type, Integer status) {
        Page<QuestionBank> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuestionBank> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like("name", name);
        }
        if (subjectId != null) {
            wrapper.eq("subject_id", subjectId);
        }
        if (StringUtils.hasText(type)) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return questionBankMapper.selectPage(page, wrapper);
    }

    public void add(QuestionBank questionBank) {
        if (questionBank.getStatus() == null) {
            questionBank.setStatus(1);
        }
        if (questionBank.getTotalCount() == null) {
            questionBank.setTotalCount(0);
        }
        questionBankMapper.insert(questionBank);
    }

    public void update(QuestionBank questionBank) {
        questionBankMapper.updateById(questionBank);
    }

    public void delete(Long id) {
        questionBankMapper.deleteById(id);
    }
}

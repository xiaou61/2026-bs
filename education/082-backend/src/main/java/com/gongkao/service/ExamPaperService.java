package com.gongkao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.entity.ExamPaper;
import com.gongkao.mapper.ExamPaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ExamPaperService {

    @Autowired
    private ExamPaperMapper examPaperMapper;

    public Page<ExamPaper> getList(int pageNum, int pageSize, String title, Long subjectId, Integer publishStatus) {
        Page<ExamPaper> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ExamPaper> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like("title", title);
        }
        if (subjectId != null) {
            wrapper.eq("subject_id", subjectId);
        }
        if (publishStatus != null) {
            wrapper.eq("publish_status", publishStatus);
        }
        wrapper.orderByDesc("create_time");
        return examPaperMapper.selectPage(page, wrapper);
    }

    public void add(ExamPaper examPaper) {
        if (examPaper.getPublishStatus() == null) {
            examPaper.setPublishStatus(0);
        }
        if (examPaper.getQuestionCount() == null) {
            examPaper.setQuestionCount(0);
        }
        examPaperMapper.insert(examPaper);
    }

    public void update(ExamPaper examPaper) {
        examPaperMapper.updateById(examPaper);
    }

    public void delete(Long id) {
        examPaperMapper.deleteById(id);
    }

    public void publish(Long id, Integer status) {
        ExamPaper examPaper = new ExamPaper();
        examPaper.setId(id);
        examPaper.setPublishStatus(status);
        examPaperMapper.updateById(examPaper);
    }
}

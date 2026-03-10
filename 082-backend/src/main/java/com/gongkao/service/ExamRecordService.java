package com.gongkao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.entity.ExamRecord;
import com.gongkao.mapper.ExamRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ExamRecordService {

    @Autowired
    private ExamRecordMapper examRecordMapper;

    public Page<ExamRecord> getList(int pageNum, int pageSize, Long userId, Long paperId, String status, Integer passStatus) {
        Page<ExamRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ExamRecord> wrapper = new QueryWrapper<>();
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (paperId != null) {
            wrapper.eq("paper_id", paperId);
        }
        if (status != null && !status.trim().isEmpty()) {
            wrapper.eq("status", status);
        }
        if (passStatus != null) {
            wrapper.eq("pass_status", passStatus);
        }
        wrapper.orderByDesc("create_time");
        return examRecordMapper.selectPage(page, wrapper);
    }

    public void add(ExamRecord examRecord) {
        if (examRecord.getStartTime() == null) {
            examRecord.setStartTime(LocalDateTime.now());
        }
        if (examRecord.getSubmitTime() == null) {
            examRecord.setSubmitTime(LocalDateTime.now());
        }
        if (examRecord.getStatus() == null || examRecord.getStatus().trim().isEmpty()) {
            examRecord.setStatus("submitted");
        }
        if (examRecord.getTotalScore() == null) {
            examRecord.setTotalScore(BigDecimal.ZERO);
        }
        if (examRecord.getObjectiveScore() == null) {
            examRecord.setObjectiveScore(BigDecimal.ZERO);
        }
        if (examRecord.getSubjectiveScore() == null) {
            examRecord.setSubjectiveScore(BigDecimal.ZERO);
        }
        if (examRecord.getPassStatus() == null) {
            examRecord.setPassStatus(0);
        }
        if (examRecord.getDurationSeconds() == null) {
            examRecord.setDurationSeconds(0);
        }
        examRecordMapper.insert(examRecord);
    }

    public void update(ExamRecord examRecord) {
        examRecordMapper.updateById(examRecord);
    }

    public void delete(Long id) {
        examRecordMapper.deleteById(id);
    }
}

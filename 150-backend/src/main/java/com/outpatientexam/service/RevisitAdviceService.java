package com.outpatientexam.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.outpatientexam.entity.RevisitAdvice;
import com.outpatientexam.mapper.RevisitAdviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevisitAdviceService {
    private final RevisitAdviceMapper patentRecordMapper;

    public PageInfo<RevisitAdvice> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(patentRecordMapper.selectPage(keyword, status));
    }

    public void save(RevisitAdvice entity) {
        if (entity.getId() == null) patentRecordMapper.insert(entity);
        else patentRecordMapper.update(entity);
    }

    public void delete(Long id) {
        patentRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        patentRecordMapper.updateStatus(id, status);
    }
}









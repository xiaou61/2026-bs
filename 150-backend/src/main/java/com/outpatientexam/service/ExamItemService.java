package com.outpatientexam.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.outpatientexam.entity.ExamItem;
import com.outpatientexam.mapper.ExamItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamItemService {
    private final ExamItemMapper researchProjectMapper;

    public PageInfo<ExamItem> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(researchProjectMapper.selectPage(keyword, status));
    }

    public void save(ExamItem entity) {
        if (entity.getId() == null) researchProjectMapper.insert(entity);
        else researchProjectMapper.update(entity);
    }

    public void delete(Long id) {
        researchProjectMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        researchProjectMapper.updateStatus(id, status);
    }
}









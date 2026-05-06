package com.outpatientexam.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.outpatientexam.entity.ExamDepartment;
import com.outpatientexam.mapper.ExamDepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamDepartmentService {
    private final ExamDepartmentMapper invoiceRecordMapper;

    public PageInfo<ExamDepartment> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(invoiceRecordMapper.selectPage(keyword, status));
    }

    public void save(ExamDepartment entity) {
        if (entity.getId() == null) invoiceRecordMapper.insert(entity);
        else invoiceRecordMapper.update(entity);
    }

    public void delete(Long id) {
        invoiceRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        invoiceRecordMapper.updateStatus(id, status);
    }
}









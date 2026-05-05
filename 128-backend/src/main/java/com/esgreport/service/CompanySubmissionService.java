package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.CompanySubmission;
import com.esgreport.mapper.CompanySubmissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanySubmissionService {
    private final CompanySubmissionMapper companySubmissionMapper;

    public PageInfo<CompanySubmission> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(companySubmissionMapper.selectPage(keyword, status));
    }

    public void save(CompanySubmission entity) {
        if (entity.getId() == null) companySubmissionMapper.insert(entity);
        else companySubmissionMapper.update(entity);
    }

    public void delete(Long id) {
        companySubmissionMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        companySubmissionMapper.updateStatus(id, status);
    }
}

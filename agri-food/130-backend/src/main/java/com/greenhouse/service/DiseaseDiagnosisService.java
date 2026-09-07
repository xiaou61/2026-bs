package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.DiseaseDiagnosis;
import com.greenhouse.mapper.DiseaseDiagnosisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseDiagnosisService {
    private final DiseaseDiagnosisMapper diseaseDiagnosisMapper;

    public PageInfo<DiseaseDiagnosis> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(diseaseDiagnosisMapper.selectPage(keyword, status));
    }

    public void save(DiseaseDiagnosis entity) {
        if (entity.getId() == null) diseaseDiagnosisMapper.insert(entity);
        else diseaseDiagnosisMapper.update(entity);
    }

    public void delete(Long id) {
        diseaseDiagnosisMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        diseaseDiagnosisMapper.updateStatus(id, status);
    }
}

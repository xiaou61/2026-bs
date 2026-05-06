package com.eldercare.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eldercare.entity.HealthAssessment;
import com.eldercare.mapper.HealthAssessmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthAssessmentService {
    private final HealthAssessmentMapper researchAchievementMapper;

    public PageInfo<HealthAssessment> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(researchAchievementMapper.selectPage(keyword, status));
    }

    public void save(HealthAssessment entity) {
        if (entity.getId() == null) researchAchievementMapper.insert(entity);
        else researchAchievementMapper.update(entity);
    }

    public void delete(Long id) {
        researchAchievementMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        researchAchievementMapper.updateStatus(id, status);
    }
}








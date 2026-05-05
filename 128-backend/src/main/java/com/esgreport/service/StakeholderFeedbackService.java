package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.StakeholderFeedback;
import com.esgreport.mapper.StakeholderFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StakeholderFeedbackService {
    private final StakeholderFeedbackMapper stakeholderFeedbackMapper;

    public PageInfo<StakeholderFeedback> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(stakeholderFeedbackMapper.selectPage(keyword, status));
    }

    public void save(StakeholderFeedback entity) {
        if (entity.getId() == null) stakeholderFeedbackMapper.insert(entity);
        else stakeholderFeedbackMapper.update(entity);
    }

    public void delete(Long id) {
        stakeholderFeedbackMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        stakeholderFeedbackMapper.updateStatus(id, status);
    }
}

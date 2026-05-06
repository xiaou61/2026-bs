package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.SealRecord;
import com.econtract.mapper.SealRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SealRecordService {
    private final SealRecordMapper researchAchievementMapper;

    public PageInfo<SealRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(researchAchievementMapper.selectPage(keyword, status));
    }

    public void save(SealRecord entity) {
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




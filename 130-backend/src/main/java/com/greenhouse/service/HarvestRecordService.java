package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.HarvestRecord;
import com.greenhouse.mapper.HarvestRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HarvestRecordService {
    private final HarvestRecordMapper harvestRecordMapper;

    public PageInfo<HarvestRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(harvestRecordMapper.selectPage(keyword, status));
    }

    public void save(HarvestRecord entity) {
        if (entity.getId() == null) harvestRecordMapper.insert(entity);
        else harvestRecordMapper.update(entity);
    }

    public void delete(Long id) {
        harvestRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        harvestRecordMapper.updateStatus(id, status);
    }
}

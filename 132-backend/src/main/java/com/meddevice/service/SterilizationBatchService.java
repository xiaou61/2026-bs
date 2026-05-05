package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.SterilizationBatch;
import com.meddevice.mapper.SterilizationBatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SterilizationBatchService {
    private final SterilizationBatchMapper sterilizationBatchMapper;

    public PageInfo<SterilizationBatch> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(sterilizationBatchMapper.selectPage(keyword, status));
    }

    public void save(SterilizationBatch entity) {
        if (entity.getId() == null) sterilizationBatchMapper.insert(entity);
        else sterilizationBatchMapper.update(entity);
    }

    public void delete(Long id) {
        sterilizationBatchMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        sterilizationBatchMapper.updateStatus(id, status);
    }
}

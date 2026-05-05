package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.CropBatch;
import com.greenhouse.mapper.CropBatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CropBatchService {
    private final CropBatchMapper cropBatchMapper;

    public PageInfo<CropBatch> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(cropBatchMapper.selectPage(keyword, status));
    }

    public void save(CropBatch entity) {
        if (entity.getId() == null) cropBatchMapper.insert(entity);
        else cropBatchMapper.update(entity);
    }

    public void delete(Long id) {
        cropBatchMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        cropBatchMapper.updateStatus(id, status);
    }
}

package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.IndicatorData;
import com.esgreport.mapper.IndicatorDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndicatorDataService {
    private final IndicatorDataMapper indicatorDataMapper;

    public PageInfo<IndicatorData> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(indicatorDataMapper.selectPage(keyword, status));
    }

    public void save(IndicatorData entity) {
        if (entity.getId() == null) indicatorDataMapper.insert(entity);
        else indicatorDataMapper.update(entity);
    }

    public void delete(Long id) {
        indicatorDataMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        indicatorDataMapper.updateStatus(id, status);
    }
}

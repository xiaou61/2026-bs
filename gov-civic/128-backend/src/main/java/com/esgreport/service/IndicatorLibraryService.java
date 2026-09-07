package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.IndicatorLibrary;
import com.esgreport.mapper.IndicatorLibraryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndicatorLibraryService {
    private final IndicatorLibraryMapper indicatorLibraryMapper;

    public PageInfo<IndicatorLibrary> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(indicatorLibraryMapper.selectPage(keyword, status));
    }

    public void save(IndicatorLibrary entity) {
        if (entity.getId() == null) indicatorLibraryMapper.insert(entity);
        else indicatorLibraryMapper.update(entity);
    }

    public void delete(Long id) {
        indicatorLibraryMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        indicatorLibraryMapper.updateStatus(id, status);
    }
}

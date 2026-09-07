package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.TripRecord;
import com.accessibletravel.mapper.TripRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripRecordService {
    private final TripRecordMapper tripRecordMapper;

    public PageInfo<TripRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(tripRecordMapper.selectPage(keyword, status));
    }

    public void save(TripRecord entity) {
        if (entity.getId() == null) tripRecordMapper.insert(entity);
        else tripRecordMapper.update(entity);
    }

    public void delete(Long id) {
        tripRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        tripRecordMapper.updateStatus(id, status);
    }
}


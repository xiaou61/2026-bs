package com.logisticspark.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logisticspark.entity.GateCheckin;
import com.logisticspark.mapper.GateCheckinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GateCheckinService {
    private final GateCheckinMapper mapper;

    public PageInfo<GateCheckin> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(GateCheckin entity) {
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.update(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}

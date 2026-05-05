package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.ReportingPeriod;
import com.esgreport.mapper.ReportingPeriodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportingPeriodService {
    private final ReportingPeriodMapper reportingPeriodMapper;

    public PageInfo<ReportingPeriod> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(reportingPeriodMapper.selectPage(keyword, status));
    }

    public void save(ReportingPeriod entity) {
        if (entity.getId() == null) reportingPeriodMapper.insert(entity);
        else reportingPeriodMapper.update(entity);
    }

    public void delete(Long id) {
        reportingPeriodMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        reportingPeriodMapper.updateStatus(id, status);
    }
}

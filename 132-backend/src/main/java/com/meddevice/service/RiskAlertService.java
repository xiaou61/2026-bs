package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.RiskAlert;
import com.meddevice.mapper.RiskAlertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskAlertService {
    private final RiskAlertMapper riskAlertMapper;

    public PageInfo<RiskAlert> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(riskAlertMapper.selectPage(keyword, status));
    }

    public void save(RiskAlert entity) {
        if (entity.getId() == null) riskAlertMapper.insert(entity);
        else riskAlertMapper.update(entity);
    }

    public void delete(Long id) {
        riskAlertMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        riskAlertMapper.updateStatus(id, status);
    }
}

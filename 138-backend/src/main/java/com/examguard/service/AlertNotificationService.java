package com.examguard.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.examguard.entity.AlertNotification;
import com.examguard.mapper.AlertNotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertNotificationService {
    private final AlertNotificationMapper riskWarningMapper;

    public PageInfo<AlertNotification> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(riskWarningMapper.selectPage(keyword, status));
    }

    public void save(AlertNotification entity) {
        if (entity.getId() == null) riskWarningMapper.insert(entity);
        else riskWarningMapper.update(entity);
    }

    public void delete(Long id) {
        riskWarningMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        riskWarningMapper.updateStatus(id, status);
    }
}



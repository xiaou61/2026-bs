package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.MaintenanceRecord;
import com.meddevice.mapper.MaintenanceRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceRecordService {
    private final MaintenanceRecordMapper maintenanceRecordMapper;

    public PageInfo<MaintenanceRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(maintenanceRecordMapper.selectPage(keyword, status));
    }

    public void save(MaintenanceRecord entity) {
        if (entity.getId() == null) maintenanceRecordMapper.insert(entity);
        else maintenanceRecordMapper.update(entity);
    }

    public void delete(Long id) {
        maintenanceRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        maintenanceRecordMapper.updateStatus(id, status);
    }
}

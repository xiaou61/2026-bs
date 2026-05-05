package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.DeviceCategory;
import com.meddevice.mapper.DeviceCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceCategoryService {
    private final DeviceCategoryMapper deviceCategoryMapper;

    public PageInfo<DeviceCategory> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(deviceCategoryMapper.selectPage(keyword, status));
    }

    public void save(DeviceCategory entity) {
        if (entity.getId() == null) deviceCategoryMapper.insert(entity);
        else deviceCategoryMapper.update(entity);
    }

    public void delete(Long id) {
        deviceCategoryMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        deviceCategoryMapper.updateStatus(id, status);
    }
}

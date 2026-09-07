package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.DeviceProfile;
import com.meddevice.mapper.DeviceProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceProfileService {
    private final DeviceProfileMapper deviceProfileMapper;

    public PageInfo<DeviceProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(deviceProfileMapper.selectPage(keyword, status));
    }

    public void save(DeviceProfile entity) {
        if (entity.getId() == null) deviceProfileMapper.insert(entity);
        else deviceProfileMapper.update(entity);
    }

    public void delete(Long id) {
        deviceProfileMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        deviceProfileMapper.updateStatus(id, status);
    }
}

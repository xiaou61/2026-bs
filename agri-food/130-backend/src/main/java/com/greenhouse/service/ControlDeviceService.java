package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.ControlDevice;
import com.greenhouse.mapper.ControlDeviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ControlDeviceService {
    private final ControlDeviceMapper controlDeviceMapper;

    public PageInfo<ControlDevice> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(controlDeviceMapper.selectPage(keyword, status));
    }

    public void save(ControlDevice entity) {
        if (entity.getId() == null) controlDeviceMapper.insert(entity);
        else controlDeviceMapper.update(entity);
    }

    public void delete(Long id) {
        controlDeviceMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        controlDeviceMapper.updateStatus(id, status);
    }
}

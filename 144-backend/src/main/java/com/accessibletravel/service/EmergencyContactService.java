package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.EmergencyContact;
import com.accessibletravel.mapper.EmergencyContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmergencyContactService {
    private final EmergencyContactMapper emergencyContactMapper;

    public PageInfo<EmergencyContact> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(emergencyContactMapper.selectPage(keyword, status));
    }

    public void save(EmergencyContact entity) {
        if (entity.getId() == null) emergencyContactMapper.insert(entity);
        else emergencyContactMapper.update(entity);
    }

    public void delete(Long id) {
        emergencyContactMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        emergencyContactMapper.updateStatus(id, status);
    }
}


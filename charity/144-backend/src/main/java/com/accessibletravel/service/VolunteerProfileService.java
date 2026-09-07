package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.VolunteerProfile;
import com.accessibletravel.mapper.VolunteerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolunteerProfileService {
    private final VolunteerProfileMapper volunteerProfileMapper;

    public PageInfo<VolunteerProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(volunteerProfileMapper.selectPage(keyword, status));
    }

    public void save(VolunteerProfile entity) {
        if (entity.getId() == null) volunteerProfileMapper.insert(entity);
        else volunteerProfileMapper.update(entity);
    }

    public void delete(Long id) {
        volunteerProfileMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        volunteerProfileMapper.updateStatus(id, status);
    }
}


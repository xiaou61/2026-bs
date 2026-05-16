package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.TravelerProfile;
import com.accessibletravel.mapper.TravelerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelerProfileService {
    private final TravelerProfileMapper travelerProfileMapper;

    public PageInfo<TravelerProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(travelerProfileMapper.selectPage(keyword, status));
    }

    public void save(TravelerProfile entity) {
        if (entity.getId() == null) travelerProfileMapper.insert(entity);
        else travelerProfileMapper.update(entity);
    }

    public void delete(Long id) {
        travelerProfileMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        travelerProfileMapper.updateStatus(id, status);
    }
}


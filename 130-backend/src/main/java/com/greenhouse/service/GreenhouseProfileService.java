package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.GreenhouseProfile;
import com.greenhouse.mapper.GreenhouseProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreenhouseProfileService {
    private final GreenhouseProfileMapper greenhouseProfileMapper;

    public PageInfo<GreenhouseProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(greenhouseProfileMapper.selectPage(keyword, status));
    }

    public void save(GreenhouseProfile entity) {
        if (entity.getId() == null) greenhouseProfileMapper.insert(entity);
        else greenhouseProfileMapper.update(entity);
    }

    public void delete(Long id) {
        greenhouseProfileMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        greenhouseProfileMapper.updateStatus(id, status);
    }
}

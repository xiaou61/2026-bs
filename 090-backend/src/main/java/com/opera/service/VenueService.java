package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.VenueInfo;
import com.opera.mapper.VenueInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueInfoMapper venueInfoMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<VenueInfo> list(String name, Long majorId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<VenueInfo> list = venueInfoMapper.selectList(name, majorId, status);
        return new PageInfo<>(list);
    }

    public List<VenueInfo> enabledList() {
        return venueInfoMapper.selectEnabled();
    }

    public void add(VenueInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getMemberCount() == null) {
            entity.setMemberCount(0);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        venueInfoMapper.insert(entity);
    }

    public void update(VenueInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("场馆ID不能为空");
        }
        validate(entity);
        venueInfoMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        venueInfoMapper.deleteById(id);
    }

    private void validate(VenueInfo entity) {
        if (entity == null || entity.getMajorId() == null || !StringUtils.hasText(entity.getName())) {
            throw new BusinessException("名家档案和场馆名称不能为空");
        }
    }
}



package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.CulturalSeason;
import com.opera.mapper.CulturalSeasonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SeasonService {

    @Autowired
    private CulturalSeasonMapper termMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<CulturalSeason> list(String termName, Integer status, Integer currentFlag, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CulturalSeason> list = termMapper.selectList(termName, status, currentFlag);
        return new PageInfo<>(list);
    }

    public List<CulturalSeason> enabledList() {
        return termMapper.selectEnabled();
    }

    public void add(CulturalSeason entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getCurrentFlag() == null) {
            entity.setCurrentFlag(0);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        termMapper.insert(entity);
    }

    public void update(CulturalSeason entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("文化专题ID不能为空");
        }
        validate(entity);
        termMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        termMapper.deleteById(id);
    }

    private void validate(CulturalSeason entity) {
        if (entity == null || !StringUtils.hasText(entity.getTermName())) {
            throw new BusinessException("文化专题名称不能为空");
        }
    }
}



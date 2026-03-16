package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.ArtistArchive;
import com.opera.mapper.ArtistArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistArchiveMapper majorMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<ArtistArchive> list(String name, Long departmentId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArtistArchive> list = majorMapper.selectList(name, departmentId, status);
        return new PageInfo<>(list);
    }

    public List<ArtistArchive> enabledList() {
        return majorMapper.selectEnabled();
    }

    public void add(ArtistArchive entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        majorMapper.insert(entity);
    }

    public void update(ArtistArchive entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("名家档案ID不能为空");
        }
        validate(entity);
        majorMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        majorMapper.deleteById(id);
    }

    private void validate(ArtistArchive entity) {
        if (entity == null || entity.getDepartmentId() == null || !StringUtils.hasText(entity.getName()) || !StringUtils.hasText(entity.getCode())) {
            throw new BusinessException("剧种分类、名家档案名称和编码不能为空");
        }
    }
}



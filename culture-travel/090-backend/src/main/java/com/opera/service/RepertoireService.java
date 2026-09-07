package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.RepertoireInfo;
import com.opera.mapper.RepertoireInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RepertoireService {

    @Autowired
    private RepertoireInfoMapper courseMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<RepertoireInfo> list(String courseName, Long artistId, Long termId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RepertoireInfo> list = courseMapper.selectList(courseName, artistId, termId, status);
        return new PageInfo<>(list);
    }

    public List<RepertoireInfo> enabledList() {
        return courseMapper.selectEnabled();
    }

    public void add(RepertoireInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        courseMapper.insert(entity);
    }

    public void update(RepertoireInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("剧目ID不能为空");
        }
        validate(entity);
        courseMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        courseMapper.deleteById(id);
    }

    public RepertoireInfo getById(Long id) {
        return courseMapper.selectById(id);
    }

    private void validate(RepertoireInfo entity) {
        if (entity == null || !StringUtils.hasText(entity.getCourseName()) || !StringUtils.hasText(entity.getCourseCode())) {
            throw new BusinessException("剧目名称和剧目编码不能为空");
        }
    }
}



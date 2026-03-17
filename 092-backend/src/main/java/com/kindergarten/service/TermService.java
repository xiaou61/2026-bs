package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.SchoolTerm;
import com.kindergarten.mapper.SchoolTermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TermService {

    @Autowired
    private SchoolTermMapper termMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<SchoolTerm> list(String termName, Integer status, Integer currentFlag, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SchoolTerm> list = termMapper.selectList(termName, status, currentFlag);
        return new PageInfo<>(list);
    }

    public List<SchoolTerm> enabledList() {
        return termMapper.selectEnabled();
    }

    public void add(SchoolTerm entity, String role) {
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

    public void update(SchoolTerm entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("学期ID不能为空");
        }
        validate(entity);
        termMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        termMapper.deleteById(id);
    }

    private void validate(SchoolTerm entity) {
        if (entity == null || !StringUtils.hasText(entity.getTermName())) {
            throw new BusinessException("学期名称不能为空");
        }
    }
}

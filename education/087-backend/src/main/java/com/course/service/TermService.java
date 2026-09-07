package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.AcademicTerm;
import com.course.mapper.AcademicTermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TermService {

    @Autowired
    private AcademicTermMapper termMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<AcademicTerm> list(String termName, Integer status, Integer currentFlag, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AcademicTerm> list = termMapper.selectList(termName, status, currentFlag);
        return new PageInfo<>(list);
    }

    public List<AcademicTerm> enabledList() {
        return termMapper.selectEnabled();
    }

    public void add(AcademicTerm entity, String role) {
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

    public void update(AcademicTerm entity, String role) {
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

    private void validate(AcademicTerm entity) {
        if (entity == null || !StringUtils.hasText(entity.getTermName())) {
            throw new BusinessException("学期名称不能为空");
        }
    }
}

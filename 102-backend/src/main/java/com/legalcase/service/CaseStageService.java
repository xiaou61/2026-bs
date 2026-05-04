package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.CaseStage;
import com.legalcase.mapper.CaseStageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CaseStageService {
    @Autowired
    private CaseStageMapper mapper;

    public PageInfo<CaseStage> page(Integer pageNum, Integer pageSize, String keyword, Long caseId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, caseId, status));
    }

    public CaseStage getById(Long id) {
        return mapper.selectById(id);
    }

    public void finish(Long id) {
        CaseStage entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "进度节点不存在");
        }
        entity.setStatus(2);
        entity.setFinishDate(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void reopen(Long id) {
        CaseStage entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "进度节点不存在");
        }
        entity.setStatus(1);
        entity.setFinishDate(null);
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }
}

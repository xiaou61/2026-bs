package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.LegalCase;
import com.legalcase.mapper.LegalCaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LegalCaseService {
    @Autowired
    private LegalCaseMapper mapper;

    public PageInfo<LegalCase> page(Integer pageNum, Integer pageSize, String keyword, Long clientId, Long lawyerId, String caseType, Integer status, String priority) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, clientId, lawyerId, caseType, status, priority));
    }

    public LegalCase getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(LegalCase entity) {
        if (entity.getId() == null) {
            entity.setCaseNo(entity.getCaseNo() == null ? "LC" + System.currentTimeMillis() : entity.getCaseNo());
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setCurrentStage(entity.getCurrentStage() == null ? "咨询受理" : entity.getCurrentStage());
            entity.setNextAction(entity.getNextAction() == null ? "补充材料并安排律师沟通" : entity.getNextAction());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.updateById(entity);
        }
    }

    public void advance(Long id) {
        LegalCase entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "案件不存在");
        }
        entity.setStatus(1);
        entity.setCurrentStage("文书准备");
        entity.setNextAction("生成法律文书并进行复核");
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void close(Long id) {
        LegalCase entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "案件不存在");
        }
        entity.setStatus(3);
        entity.setCurrentStage("已结案");
        entity.setNextAction("归档案件材料和费用记录");
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

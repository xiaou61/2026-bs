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

    @Autowired
    private AuthService authService;

    public PageInfo<LegalCase> page(Integer pageNum, Integer pageSize, String keyword, Long clientId, Long lawyerId, String caseType, Integer status, String priority) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, clientId, lawyerId, caseType, status, priority));
    }

    public PageInfo<LegalCase> pageByRole(Integer pageNum, Integer pageSize, String keyword, Long clientId, Long lawyerId, String caseType, Integer status, String priority, Long userId, String role) {
        Long targetClientId = clientId;
        Long targetLawyerId = lawyerId;
        if (authService.isClient(role)) {
            targetClientId = authService.requireClientId(userId);
        }
        if (authService.isLawyer(role)) {
            targetLawyerId = authService.requireLawyerId(userId);
        }
        return page(pageNum, pageSize, keyword, targetClientId, targetLawyerId, caseType, status, priority);
    }

    public LegalCase getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(LegalCase entity, Long userId, String role) {
        if (entity.getId() == null) {
            entity.setCaseNo(entity.getCaseNo() == null ? "LC" + System.currentTimeMillis() : entity.getCaseNo());
            entity.setStatus(0);
            entity.setCurrentStage(entity.getCurrentStage() == null ? "咨询受理" : entity.getCurrentStage());
            entity.setNextAction(entity.getNextAction() == null ? "补充材料并安排律师沟通" : entity.getNextAction());
            if (authService.isLawyer(role)) {
                entity.setLawyerId(authService.requireLawyerId(userId));
            }
            entity.setCreateTime(LocalDateTime.now());
        } else {
            LegalCase db = mapper.selectById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "案件不存在");
            }
            assertCanHandle(db, userId, role);
            entity.setCaseNo(db.getCaseNo());
            entity.setClientId(db.getClientId());
            entity.setLawyerId(db.getLawyerId());
            entity.setStatus(db.getStatus());
            entity.setCurrentStage(db.getCurrentStage());
            entity.setNextAction(db.getNextAction());
            entity.setCreateTime(db.getCreateTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.updateById(entity);
        }
    }

    public void advance(Long id, Long userId, String role) {
        LegalCase entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "案件不存在");
        }
        assertCanHandle(entity, userId, role);
        entity.setStatus(1);
        entity.setCurrentStage("文书准备");
        entity.setNextAction("生成法律文书并进行复核");
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void close(Long id, Long userId, String role) {
        LegalCase entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "案件不存在");
        }
        assertCanHandle(entity, userId, role);
        entity.setStatus(3);
        entity.setCurrentStage("已结案");
        entity.setNextAction("归档案件材料和费用记录");
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void delete(Long id, Long userId, String role) {
        LegalCase entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "案件不存在");
        }
        assertCanHandle(entity, userId, role);
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }

    private void assertCanHandle(LegalCase entity, Long userId, String role) {
        if (authService.isLawyer(role) && !authService.requireLawyerId(userId).equals(entity.getLawyerId())) {
            throw new BusinessException(403, "只能操作本人承办案件");
        }
    }
}

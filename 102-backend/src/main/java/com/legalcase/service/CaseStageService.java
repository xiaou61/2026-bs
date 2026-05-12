package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.CaseStage;
import com.legalcase.entity.LegalCase;
import com.legalcase.mapper.CaseStageMapper;
import com.legalcase.mapper.LegalCaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CaseStageService {
    @Autowired
    private CaseStageMapper mapper;

    @Autowired
    private LegalCaseMapper legalCaseMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<CaseStage> page(Integer pageNum, Integer pageSize, String keyword, Long caseId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, caseId, status));
    }

    public PageInfo<CaseStage> pageByRole(Integer pageNum, Integer pageSize, String keyword, Long caseId, Integer status, Long userId, String role) {
        PageHelper.startPage(pageNum, pageSize);
        if (authService.isLawyer(role)) {
            return new PageInfo<>(mapper.selectPageByLawyerId(keyword, caseId, status, authService.requireLawyerId(userId)));
        }
        return new PageInfo<>(mapper.selectPage(keyword, caseId, status));
    }

    public CaseStage getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(CaseStage entity, Long userId, String role) {
        LegalCase legalCase = requireCase(entity.getCaseId());
        assertCanHandle(legalCase, userId, role);
        if (entity.getId() == null) {
            entity.setStatus(0);
            entity.setFinishDate(null);
            entity.setCreateTime(LocalDateTime.now());
        } else {
            CaseStage db = mapper.selectById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "进度节点不存在");
            }
            entity.setStatus(db.getStatus());
            entity.setFinishDate(db.getFinishDate());
            entity.setCreateTime(db.getCreateTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.updateById(entity);
        }
    }

    public void finish(Long id, Long userId, String role) {
        CaseStage entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "进度节点不存在");
        }
        assertCanHandle(requireCase(entity.getCaseId()), userId, role);
        entity.setStatus(2);
        entity.setFinishDate(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void reopen(Long id, Long userId, String role) {
        CaseStage entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "进度节点不存在");
        }
        assertCanHandle(requireCase(entity.getCaseId()), userId, role);
        entity.setStatus(1);
        entity.setFinishDate(null);
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void delete(Long id, Long userId, String role) {
        CaseStage entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "进度节点不存在");
        }
        assertCanHandle(requireCase(entity.getCaseId()), userId, role);
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }

    private LegalCase requireCase(Long caseId) {
        LegalCase legalCase = legalCaseMapper.selectById(caseId);
        if (legalCase == null) {
            throw new BusinessException(400, "案件不存在");
        }
        return legalCase;
    }

    private void assertCanHandle(LegalCase legalCase, Long userId, String role) {
        if (authService.isLawyer(role) && !authService.requireLawyerId(userId).equals(legalCase.getLawyerId())) {
            throw new BusinessException(403, "只能操作本人承办案件");
        }
    }
}

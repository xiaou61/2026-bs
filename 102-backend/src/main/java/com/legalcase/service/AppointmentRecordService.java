package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.AppointmentRecord;
import com.legalcase.entity.LegalCase;
import com.legalcase.mapper.AppointmentRecordMapper;
import com.legalcase.mapper.LegalCaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentRecordService {
    @Autowired
    private AppointmentRecordMapper mapper;

    @Autowired
    private LegalCaseMapper legalCaseMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<AppointmentRecord> page(Integer pageNum, Integer pageSize, String keyword, Long caseId, Long clientId, Long lawyerId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, caseId, clientId, lawyerId, status));
    }

    public PageInfo<AppointmentRecord> pageByRole(Integer pageNum, Integer pageSize, String keyword, Long caseId, Long clientId, Long lawyerId, Integer status, Long userId, String role) {
        Long targetClientId = clientId;
        Long targetLawyerId = lawyerId;
        if (authService.isClient(role)) {
            targetClientId = authService.requireClientId(userId);
        }
        if (authService.isLawyer(role)) {
            targetLawyerId = authService.requireLawyerId(userId);
        }
        return page(pageNum, pageSize, keyword, caseId, targetClientId, targetLawyerId, status);
    }

    public AppointmentRecord getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(AppointmentRecord entity, Long userId, String role) {
        LegalCase legalCase = requireCase(entity.getCaseId());
        assertCanHandle(legalCase, userId, role);
        entity.setClientId(legalCase.getClientId());
        entity.setLawyerId(legalCase.getLawyerId());
        if (entity.getId() == null) {
            entity.setStatus(0);
            entity.setCreateTime(LocalDateTime.now());
        } else {
            AppointmentRecord db = mapper.selectById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "预约记录不存在");
            }
            entity.setStatus(db.getStatus());
            entity.setCreateTime(db.getCreateTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.updateById(entity);
    }

    public void updateStatus(Long id, Integer status, Long userId, String role) {
        AppointmentRecord entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "预约记录不存在");
        }
        assertCanHandle(requireCase(entity.getCaseId()), userId, role);
        entity.setStatus(status);
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void delete(Long id, Long userId, String role) {
        AppointmentRecord entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "预约记录不存在");
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

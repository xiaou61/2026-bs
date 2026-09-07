package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.ClientProfile;
import com.legalcase.entity.EvidenceMaterial;
import com.legalcase.mapper.ClientProfileMapper;
import com.legalcase.mapper.EvidenceMaterialMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EvidenceMaterialService {
    @Autowired
    private EvidenceMaterialMapper mapper;

    @Autowired
    private ClientProfileMapper clientProfileMapper;

    public PageInfo<EvidenceMaterial> page(Integer pageNum, Integer pageSize, String keyword, Long caseId, Integer verifyStatus) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, caseId, verifyStatus));
    }

    public PageInfo<EvidenceMaterial> pageByRole(Integer pageNum, Integer pageSize, String keyword, Long caseId, Integer verifyStatus, Long userId, String role) {
        PageHelper.startPage(pageNum, pageSize);
        if ("CLIENT".equals(role)) {
            return new PageInfo<>(mapper.selectPageByClientId(keyword, caseId, verifyStatus, getOwnClientId(userId)));
        }
        return new PageInfo<>(mapper.selectPage(keyword, caseId, verifyStatus));
    }

    public EvidenceMaterial getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(EvidenceMaterial entity) {
        if (entity.getId() == null) {
            entity.setVerifyStatus(0);
            entity.setCreateTime(LocalDateTime.now());
        } else {
            EvidenceMaterial db = mapper.selectById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "证据材料不存在");
            }
            entity.setVerifyStatus(db.getVerifyStatus());
            entity.setReviewComment(db.getReviewComment());
            entity.setCreateTime(db.getCreateTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.updateById(entity);
    }

    public void verify(Long id, String comment) {
        EvidenceMaterial entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "证据材料不存在");
        }
        entity.setVerifyStatus(1);
        entity.setReviewComment(comment);
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void reject(Long id, String comment) {
        EvidenceMaterial entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "证据材料不存在");
        }
        entity.setVerifyStatus(2);
        entity.setReviewComment(comment);
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }

    private Long getOwnClientId(Long userId) {
        ClientProfile profile = clientProfileMapper.selectByUserId(userId);
        if (profile == null) {
            throw new BusinessException(400, "请先创建委托人档案");
        }
        return profile.getId();
    }
}

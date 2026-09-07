package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.LegalDocument;
import com.legalcase.mapper.LegalDocumentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LegalDocumentService {
    @Autowired
    private LegalDocumentMapper mapper;

    public PageInfo<LegalDocument> page(Integer pageNum, Integer pageSize, String keyword, Long caseId, Long templateId, String documentType, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, caseId, templateId, documentType, status));
    }

    public LegalDocument getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(LegalDocument entity) {
        if (entity.getId() == null) {
            entity.setDocumentNo(entity.getDocumentNo() == null ? "DOC" + System.currentTimeMillis() : entity.getDocumentNo());
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.updateById(entity);
        }
    }

    public void generate(Long id, Long userId) {
        LegalDocument entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "法律文书不存在");
        }
        entity.setStatus(1);
        entity.setGeneratedBy(userId);
        if (entity.getContent() == null || entity.getContent().isBlank()) {
            entity.setContent("系统根据案件信息、文书模板和咨询记录生成初稿，请律师复核后归档。");
        }
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void review(LegalDocument source) {
        LegalDocument entity = mapper.selectById(source.getId());
        if (entity == null) {
            throw new BusinessException(400, "法律文书不存在");
        }
        entity.setStatus(source.getStatus() == null ? 2 : source.getStatus());
        entity.setReviewComment(source.getReviewComment() == null ? "文书复核完成" : source.getReviewComment());
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

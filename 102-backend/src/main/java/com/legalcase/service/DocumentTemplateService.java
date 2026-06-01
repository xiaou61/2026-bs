package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.DocumentTemplate;
import com.legalcase.mapper.DocumentTemplateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentTemplateService {
    @Autowired
    private DocumentTemplateMapper mapper;

    public PageInfo<DocumentTemplate> page(Integer pageNum, Integer pageSize, String keyword, String templateType, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, templateType, status));
    }

    public DocumentTemplate getById(Long id) {
        return mapper.selectById(id);
    }

    public void enable(Long id) {
        DocumentTemplate entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "文书模板不存在");
        }
        entity.setStatus(1);
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void disable(Long id) {
        DocumentTemplate entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "文书模板不存在");
        }
        entity.setStatus(0);
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void saveEntity(DocumentTemplate entity) {
        if (entity.getId() == null) {
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            if (entity.getStatus() == null) {
                entity.setStatus(1);
            }
            mapper.insert(entity);
        } else {
            entity.setUpdateTime(LocalDateTime.now());
            mapper.updateById(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }
}

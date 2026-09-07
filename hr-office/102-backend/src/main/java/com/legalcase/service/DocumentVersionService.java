package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.DocumentVersion;
import com.legalcase.mapper.DocumentVersionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentVersionService {
    @Autowired
    private DocumentVersionMapper mapper;

    public PageInfo<DocumentVersion> page(Integer pageNum, Integer pageSize, String keyword, Long documentId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, documentId));
    }

    public DocumentVersion getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(DocumentVersion entity) {
        if (entity.getId() == null) {
            
            entity.setCreateTime(LocalDateTime.now());
        }
        
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
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

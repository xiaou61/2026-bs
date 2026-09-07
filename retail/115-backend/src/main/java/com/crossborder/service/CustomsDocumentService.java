package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.CustomsDocument;
import com.crossborder.mapper.CustomsDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomsDocumentService extends BaseCrudService<CustomsDocument> {
    private final CustomsDocumentMapper mapper;

    @Override
    protected BaseMapper<CustomsDocument> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"document_no", "order_no", "document_type", "audit_user"};
    }
}

package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.DataAttachment;
import com.carbonmanage.mapper.DataAttachmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataAttachmentService extends BaseCrudService<DataAttachment> {
    private final DataAttachmentMapper dataAttachmentMapper;

    @Override
    protected BaseMapper<DataAttachment> mapper() {
        return dataAttachmentMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"attach_no", "company_no", "file_name", "file_type"};
    }
}

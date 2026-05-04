package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.AccessRequest;
import com.datamasking.mapper.AccessRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessRequestService extends BaseCrudService<AccessRequest> {
    private final AccessRequestMapper mapper;

    @Override
    protected BaseMapper<AccessRequest> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "requester_name", "dataset_name", "purpose_text"};
    }

}

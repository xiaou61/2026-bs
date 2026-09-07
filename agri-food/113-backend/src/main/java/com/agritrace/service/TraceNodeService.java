package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.TraceNode;
import com.agritrace.mapper.TraceNodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraceNodeService extends BaseCrudService<TraceNode> {
    private final TraceNodeMapper mapper;

    @Override
    protected BaseMapper<TraceNode> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"node_name", "node_code", "node_type", "region_name"};
    }

}

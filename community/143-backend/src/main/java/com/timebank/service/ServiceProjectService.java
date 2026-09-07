package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ServiceProject;
import com.timebank.mapper.ServiceProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceProjectService extends BaseCrudService<ServiceProject> {
    private final ServiceProjectMapper serviceProjectMapper;

    @Override
    protected BaseMapper<ServiceProject> mapper() {
        return serviceProjectMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"project_no", "project_name", "service_topic", "service_target"};
    }
}


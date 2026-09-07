package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ServiceCheckin;
import com.timebank.mapper.ServiceCheckinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCheckinService extends BaseCrudService<ServiceCheckin> {
    private final ServiceCheckinMapper serviceCheckinMapper;

    @Override
    protected BaseMapper<ServiceCheckin> mapper() {
        return serviceCheckinMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"checkin_no", "project_name", "checkin_name", "service_result"};
    }
}


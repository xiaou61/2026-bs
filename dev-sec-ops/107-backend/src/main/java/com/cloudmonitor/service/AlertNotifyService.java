package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.AlertNotify;
import com.cloudmonitor.mapper.AlertNotifyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertNotifyService extends BaseCrudService<AlertNotify> {
    private final AlertNotifyMapper mapper;

    @Override
    protected BaseMapper<AlertNotify> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"channel_name", "receiver", "send_status", "send_time"};
    }


}

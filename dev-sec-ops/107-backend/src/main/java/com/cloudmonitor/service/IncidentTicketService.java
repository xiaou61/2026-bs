package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.IncidentTicket;
import com.cloudmonitor.mapper.IncidentTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncidentTicketService extends BaseCrudService<IncidentTicket> {
    private final IncidentTicketMapper mapper;

    @Override
    protected BaseMapper<IncidentTicket> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"ticket_no", "title", "assignee", "priority"};
    }


}

package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.ComplaintTicket;
import com.noisemonitor.mapper.ComplaintTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintTicketService extends BaseCrudService<ComplaintTicket> {
    private final ComplaintTicketMapper complaintTicketMapper;

    @Override
    protected BaseMapper<ComplaintTicket> mapper() {
        return complaintTicketMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"ticket_no", "complaint_title", "noise_type", "complaint_area"};
    }
}







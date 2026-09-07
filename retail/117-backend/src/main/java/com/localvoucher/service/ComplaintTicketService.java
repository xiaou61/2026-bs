package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.ComplaintTicket;
import com.localvoucher.mapper.ComplaintTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintTicketService extends BaseCrudService<ComplaintTicket> {
    private final ComplaintTicketMapper mapper;

    @Override
    protected BaseMapper<ComplaintTicket> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"ticket_no", "consumer_name", "merchant_name", "issue_type"};
    }
}

package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.ComplaintTicket;
import com.noisemonitor.mapper.ComplaintTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintTicketService extends BaseCrudService<ComplaintTicket> {
    private final ComplaintTicketMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<ComplaintTicket> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}







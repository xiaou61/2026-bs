package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.TimeExchange;
import com.timebank.mapper.TimeExchangeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeExchangeService extends BaseCrudService<TimeExchange> {
    private final TimeExchangeMapper inboundRecordMapper;

    @Override
    protected BaseMapper<TimeExchange> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}






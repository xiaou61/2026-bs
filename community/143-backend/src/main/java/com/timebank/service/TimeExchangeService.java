package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.TimeExchange;
import com.timebank.mapper.TimeExchangeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeExchangeService extends BaseCrudService<TimeExchange> {
    private final TimeExchangeMapper timeExchangeMapper;

    @Override
    protected BaseMapper<TimeExchange> mapper() {
        return timeExchangeMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"exchange_no", "project_name", "exchanger_name", "exchange_time"};
    }
}


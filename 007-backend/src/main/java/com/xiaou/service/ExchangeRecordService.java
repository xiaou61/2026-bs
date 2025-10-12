package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.ExchangeRecord;
import com.xiaou.mapper.ExchangeRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRecordService extends ServiceImpl<ExchangeRecordMapper, ExchangeRecord> {

    public List<ExchangeRecord> getUserExchanges(Long userId) {
        return this.list(new QueryWrapper<ExchangeRecord>()
                .eq("user_id", userId)
                .orderByDesc("exchange_time"));
    }
}


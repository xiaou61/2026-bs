package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.TimeAccount;
import com.timebank.mapper.TimeAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeAccountService extends BaseCrudService<TimeAccount> {
    private final TimeAccountMapper timeAccountMapper;

    @Override
    protected BaseMapper<TimeAccount> mapper() {
        return timeAccountMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"account_no", "project_name", "account_type", "owner_name"};
    }
}


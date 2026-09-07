package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.ConsumerProfile;
import com.localvoucher.mapper.ConsumerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerProfileService extends BaseCrudService<ConsumerProfile> {
    private final ConsumerProfileMapper mapper;

    @Override
    protected BaseMapper<ConsumerProfile> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumer_name", "consumer_no", "phone", "level_name"};
    }
}

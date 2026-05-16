package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.NoiseSource;
import com.noisemonitor.mapper.NoiseSourceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoiseSourceService extends BaseCrudService<NoiseSource> {
    private final NoiseSourceMapper noiseSourceMapper;

    @Override
    protected BaseMapper<NoiseSource> mapper() {
        return noiseSourceMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"source_no", "source_name", "responsible_unit", "source_type"};
    }
}







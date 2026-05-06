package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.NoiseSource;
import com.noisemonitor.mapper.NoiseSourceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoiseSourceService extends BaseCrudService<NoiseSource> {
    private final NoiseSourceMapper labRoomMapper;

    @Override
    protected BaseMapper<NoiseSource> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}







package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.GuidanceScreen;
import com.parkingguide.mapper.GuidanceScreenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuidanceScreenService extends BaseCrudService<GuidanceScreen> {
    private final GuidanceScreenMapper mapper;

    @Override
    protected BaseMapper<GuidanceScreen> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"screen_no", "lot_name", "position_name", "display_text"};
    }
}

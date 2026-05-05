package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.LogisticsTrack;
import com.crossborder.mapper.LogisticsTrackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogisticsTrackService extends BaseCrudService<LogisticsTrack> {
    private final LogisticsTrackMapper mapper;

    @Override
    protected BaseMapper<LogisticsTrack> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"tracking_no", "order_no", "carrier_name", "current_node"};
    }
}

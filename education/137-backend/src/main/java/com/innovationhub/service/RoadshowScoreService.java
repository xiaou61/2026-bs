package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.RoadshowScore;
import com.innovationhub.mapper.RoadshowScoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoadshowScoreService extends BaseCrudService<RoadshowScore> {
    private final RoadshowScoreMapper inboundRecordMapper;

    @Override
    protected BaseMapper<RoadshowScore> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}



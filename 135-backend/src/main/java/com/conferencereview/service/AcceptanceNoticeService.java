package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.AcceptanceNotice;
import com.conferencereview.mapper.AcceptanceNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcceptanceNoticeService extends BaseCrudService<AcceptanceNotice> {
    private final AcceptanceNoticeMapper inboundRecordMapper;

    @Override
    protected BaseMapper<AcceptanceNotice> mapper() {
        return inboundRecordMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"inbound_no", "order_no", "consumable_name", "inbound_qty"};
    }
}


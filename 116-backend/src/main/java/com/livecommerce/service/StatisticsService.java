package com.livecommerce.service;

import com.livecommerce.mapper.AfterSaleTicketMapper;
import com.livecommerce.mapper.AnchorPerformanceMapper;
import com.livecommerce.mapper.LiveOrderMapper;
import com.livecommerce.mapper.LiveSessionMapper;
import com.livecommerce.mapper.ProductSelectionMapper;
import com.livecommerce.mapper.RefundRecordMapper;
import com.livecommerce.mapper.SchedulePlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ProductSelectionMapper selectionMapper;
    private final LiveSessionMapper sessionMapper;
    private final SchedulePlanMapper scheduleMapper;
    private final LiveOrderMapper orderMapper;
    private final AfterSaleTicketMapper ticketMapper;
    private final RefundRecordMapper refundMapper;
    private final AnchorPerformanceMapper performanceMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("selectionCount", selectionMapper.countAll());
        data.put("sessionCount", sessionMapper.countAll());
        data.put("scheduleCount", scheduleMapper.countAll());
        data.put("orderCount", orderMapper.countAll());
        data.put("ticketCount", ticketMapper.countAll());
        data.put("refundCount", refundMapper.countAll());
        data.put("performanceCount", performanceMapper.countAll());
        data.put("gmvTrend", Arrays.asList(12, 18, 26, 34, 42, 58, 66));
        data.put("ticketPie", Arrays.asList(map("仅退款", 36), map("退货退款", 26), map("物流异常", 22), map("质量问题", 16)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}

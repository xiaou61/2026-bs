package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.mapper.StatisticsMapper;
import com.xiaou.wedding.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public Map<String, Object> dashboard(String date) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerCount", statisticsMapper.countCustomer());
        map.put("orderCount", statisticsMapper.countOrder());
        map.put("orderAmount", statisticsMapper.sumOrderAmount());
        if (date != null) {
            map.put("appointmentCount", statisticsMapper.countAppointmentByDate(date));
        }
        return map;
    }
}

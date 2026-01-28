package com.agriculture.service;

import com.agriculture.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CropMapper cropMapper;

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Autowired
    private ProductionPlanMapper planMapper;

    @Autowired
    private PestWarningMapper warningMapper;

    @Autowired
    private ExpertAppointmentMapper appointmentMapper;

    public Map<String, Object> getOverview() {
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", userMapper.selectCount(null));
        result.put("cropCount", cropMapper.selectCount(null));
        result.put("knowledgeCount", knowledgeMapper.selectCount(null));
        result.put("planCount", planMapper.selectCount(null));
        result.put("warningCount", warningMapper.selectCount(null));
        result.put("appointmentCount", appointmentMapper.selectCount(null));
        return result;
    }
}

package com.teachres.service;

import com.teachres.mapper.EvalRecordMapper;
import com.teachres.mapper.EvalTaskMapper;
import com.teachres.mapper.MathCourseMapper;
import com.teachres.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private MathCourseMapper courseMapper;

    @Autowired
    private EvalTaskMapper taskMapper;

    @Autowired
    private EvalRecordMapper recordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", sysUserMapper.selectList(null, null, 1).size());
        result.put("courseCount", courseMapper.countAll());
        result.put("taskCount", taskMapper.countAll());
        result.put("recordCount", recordMapper.countAll());
        BigDecimal avgScore = recordMapper.avgScoreAll();
        result.put("avgScore", avgScore == null ? BigDecimal.ZERO : avgScore);
        result.put("monthTrend", recordMapper.selectMonthTrend());
        result.put("courseTop", recordMapper.selectCourseTop5());
        return result;
    }
}

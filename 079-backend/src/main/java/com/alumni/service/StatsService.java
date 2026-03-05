package com.alumni.service;

import com.alumni.entity.DonationRecord;
import com.alumni.mapper.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AlumniInfoMapper alumniInfoMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private DonationRecordMapper donationRecordMapper;

    @Autowired
    private ForumPostMapper forumPostMapper;

    public Map<String, Object> overview() {
        Map<String, Object> result = new HashMap<>();
        result.put("alumniCount", alumniInfoMapper.selectCount(null));
        result.put("activityCount", activityMapper.selectCount(null));
        result.put("newsCount", newsMapper.selectCount(null));
        result.put("postCount", forumPostMapper.selectCount(null));
        BigDecimal donationTotal = donationRecordMapper.selectList(
                new LambdaQueryWrapper<DonationRecord>().eq(DonationRecord::getStatus, 1))
                .stream()
                .map(DonationRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("donationTotal", donationTotal);
        return result;
    }
}

package com.timebank.service;

import com.timebank.mapper.ServiceProjectMapper;
import com.timebank.mapper.ServiceCategoryMapper;
import com.timebank.mapper.ResidentProfileMapper;
import com.timebank.mapper.VolunteerProfileMapper;
import com.timebank.mapper.ServiceBookingMapper;
import com.timebank.mapper.ServiceCheckinMapper;
import com.timebank.mapper.TimeAccountMapper;
import com.timebank.mapper.TimeExchangeMapper;
import com.timebank.mapper.FeedbackReviewMapper;
import com.timebank.mapper.CommunityActivityMapper;
import com.timebank.mapper.PointRuleMapper;
import com.timebank.mapper.SiteNoticeMapper;
import com.timebank.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ServiceProjectMapper serviceProjectMapper;
    private final ServiceCategoryMapper serviceCategoryMapper;
    private final ResidentProfileMapper residentProfileMapper;
    private final VolunteerProfileMapper volunteerProfileMapper;
    private final ServiceBookingMapper serviceBookingMapper;
    private final ServiceCheckinMapper serviceCheckinMapper;
    private final TimeAccountMapper timeAccountMapper;
    private final TimeExchangeMapper timeExchangeMapper;
    private final FeedbackReviewMapper feedbackReviewMapper;
    private final CommunityActivityMapper communityActivityMapper;
    private final PointRuleMapper pointRuleMapper;
    private final SiteNoticeMapper siteNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("projectCount", serviceProjectMapper.selectCount(null));
        data.put("bookingCount", serviceBookingMapper.selectCount(null));
        data.put("accountCount", timeAccountMapper.selectCount(null));
        data.put("noticeCount", siteNoticeMapper.selectCount(null));
        data.put("serviceTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("servicePie", Arrays.asList(map("待服务", 35), map("服务中", 31), map("已完成", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}









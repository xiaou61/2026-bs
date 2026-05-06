package com.accessibletravel.service;

import com.accessibletravel.mapper.AccessibleRouteMapper;
import com.accessibletravel.mapper.FacilityPointMapper;
import com.accessibletravel.mapper.TravelerProfileMapper;
import com.accessibletravel.mapper.AssistRequestMapper;
import com.accessibletravel.mapper.VolunteerProfileMapper;
import com.accessibletravel.mapper.RoutePlanMapper;
import com.accessibletravel.mapper.AssistTaskMapper;
import com.accessibletravel.mapper.ServiceCheckinMapper;
import com.accessibletravel.mapper.FeedbackReviewMapper;
import com.accessibletravel.mapper.EmergencyContactMapper;
import com.accessibletravel.mapper.TripRecordMapper;
import com.accessibletravel.mapper.MessageNoticeMapper;
import com.accessibletravel.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final AccessibleRouteMapper accessibleRouteMapper;
    private final FacilityPointMapper facilityPointMapper;
    private final TravelerProfileMapper travelerProfileMapper;
    private final AssistRequestMapper assistRequestMapper;
    private final VolunteerProfileMapper volunteerProfileMapper;
    private final RoutePlanMapper routePlanMapper;
    private final AssistTaskMapper assistTaskMapper;
    private final ServiceCheckinMapper serviceCheckinMapper;
    private final FeedbackReviewMapper feedbackReviewMapper;
    private final EmergencyContactMapper emergencyContactMapper;
    private final TripRecordMapper tripRecordMapper;
    private final MessageNoticeMapper messageNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("routeCount", accessibleRouteMapper.countAll());
        data.put("volunteerCount", volunteerProfileMapper.countAll());
        data.put("taskCount", assistTaskMapper.countAll());
        data.put("tripCount", tripRecordMapper.countAll());
        data.put("travelTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("travelPie", Arrays.asList(map("待出行", 35), map("协助中", 31), map("已完成", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}









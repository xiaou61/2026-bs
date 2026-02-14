package com.student.service;

import com.student.mapper.ActivityMapper;
import com.student.mapper.ApplyMapper;
import com.student.mapper.CourseMapper;
import com.student.mapper.EnrollMapper;
import com.student.mapper.JobMapper;
import com.student.mapper.LostFoundMapper;
import com.student.mapper.NoticeMapper;
import com.student.mapper.SignupMapper;
import com.student.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private EnrollMapper enrollMapper;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private SignupMapper signupMapper;

    @Resource
    private JobMapper jobMapper;

    @Resource
    private ApplyMapper applyMapper;

    @Resource
    private LostFoundMapper lostFoundMapper;

    @Resource
    private NoticeMapper noticeMapper;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", defaultZero(userMapper.countAll()));
        map.put("courseCount", defaultZero(courseMapper.countAll()));
        map.put("enrollCount", defaultZero(enrollMapper.countAll()));
        map.put("activityCount", defaultZero(activityMapper.countAll()));
        map.put("signupCount", defaultZero(signupMapper.countAll()));
        map.put("jobCount", defaultZero(jobMapper.countAll()));
        map.put("applyCount", defaultZero(applyMapper.countAll()));
        map.put("lostCount", defaultZero(lostFoundMapper.countAll()));
        map.put("noticeCount", defaultZero(noticeMapper.countAll()));
        return map;
    }

    public Map<String, Object> trend() {
        LocalDate startDay = LocalDate.now().minusDays(6);
        LocalDateTime start = startDay.atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();

        Map<String, Long> enrollMap = toDayMap(enrollMapper.dailyCreatedCount(start, end));
        Map<String, Long> signupMap = toDayMap(signupMapper.dailyCreatedCount(start, end));
        Map<String, Long> applyMap = toDayMap(applyMapper.dailyCreatedCount(start, end));

        List<Map<String, Object>> daily = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            String day = LocalDate.now().minusDays(i).toString();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("day", day);
            item.put("enrollCount", enrollMap.getOrDefault(day, 0L));
            item.put("signupCount", signupMap.getOrDefault(day, 0L));
            item.put("applyCount", applyMap.getOrDefault(day, 0L));
            daily.add(item);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("daily", daily);
        return result;
    }

    private Map<String, Long> toDayMap(List<Map<String, Object>> rows) {
        Map<String, Long> map = new HashMap<>();
        for (Map<String, Object> row : rows) {
            map.put(row.get("day").toString(), Long.parseLong(row.get("total").toString()));
        }
        return map;
    }

    private Long defaultZero(Long value) {
        return value == null ? 0L : value;
    }
}

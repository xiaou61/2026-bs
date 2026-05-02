package com.eldercare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eldercare.entity.CheckAppointment;
import com.eldercare.entity.ElderProfile;
import com.eldercare.entity.FollowUpRecord;
import com.eldercare.entity.SystemNotice;
import com.eldercare.mapper.CheckAppointmentMapper;
import com.eldercare.mapper.CheckResultMapper;
import com.eldercare.mapper.ElderProfileMapper;
import com.eldercare.mapper.FollowUpRecordMapper;
import com.eldercare.mapper.SystemNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private ElderProfileMapper elderProfileMapper;

    @Autowired
    private CheckAppointmentMapper checkAppointmentMapper;

    @Autowired
    private CheckResultMapper checkResultMapper;

    @Autowired
    private FollowUpRecordMapper followUpRecordMapper;

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;

    @Autowired
    private WarningService warningService;

    @Autowired
    private AppointmentService appointmentService;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        long elderCount = elderProfileMapper.selectCount(new QueryWrapper<ElderProfile>());
        long todayAppointmentCount = appointmentService.countToday();
        long abnormalCount = warningService.countAll();
        long resultCount = checkResultMapper.selectCount(new QueryWrapper<>());
        long followUpCount = followUpRecordMapper.selectCount(new QueryWrapper<FollowUpRecord>());
        long noticeCount = systemNoticeMapper.selectCount(new QueryWrapper<SystemNotice>().eq("status", 1));

        long totalAppointment = appointmentService.countAll();
        long finishedAppointment = appointmentService.countFinished();
        BigDecimal finishRate = BigDecimal.ZERO;
        if (totalAppointment > 0) {
            finishRate = BigDecimal.valueOf(finishedAppointment)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(totalAppointment), 2, RoundingMode.HALF_UP);
        }

        data.put("elderCount", elderCount);
        data.put("todayAppointmentCount", todayAppointmentCount);
        data.put("abnormalCount", abnormalCount);
        data.put("resultCount", resultCount);
        data.put("followUpCount", followUpCount);
        data.put("noticeCount", noticeCount);
        data.put("finishRate", finishRate);
        data.put("monthTrend", buildMonthTrend());
        data.put("warningDistribution", warningService.distribution());
        return data;
    }

    private List<Map<String, Object>> buildMonthTrend() {
        QueryWrapper<CheckAppointment> wrapper = new QueryWrapper<>();
        wrapper.select("create_time").orderByAsc("create_time");
        List<CheckAppointment> appointments = checkAppointmentMapper.selectList(wrapper);
        Map<String, Long> counts = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (CheckAppointment appointment : appointments) {
            if (appointment.getCreateTime() == null) {
                continue;
            }
            String month = appointment.getCreateTime().format(formatter);
            counts.put(month, counts.getOrDefault(month, 0L) + 1);
        }
        List<Map<String, Object>> trend = new ArrayList<>();
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("month", entry.getKey());
            item.put("value", entry.getValue());
            trend.add(item);
        }
        return trend;
    }
}

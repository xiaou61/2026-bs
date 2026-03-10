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

        QueryWrapper<CheckAppointment> monthWrapper = new QueryWrapper<>();
        monthWrapper.select("DATE_FORMAT(create_time,'%Y-%m') AS month", "COUNT(*) AS value")
                .groupBy("DATE_FORMAT(create_time,'%Y-%m')")
                .orderByAsc("month");
        List<Map<String, Object>> monthTrend = checkAppointmentMapper.selectMaps(monthWrapper);

        data.put("elderCount", elderCount);
        data.put("todayAppointmentCount", todayAppointmentCount);
        data.put("abnormalCount", abnormalCount);
        data.put("resultCount", resultCount);
        data.put("followUpCount", followUpCount);
        data.put("noticeCount", noticeCount);
        data.put("finishRate", finishRate);
        data.put("monthTrend", monthTrend);
        data.put("warningDistribution", warningService.distribution());
        return data;
    }
}

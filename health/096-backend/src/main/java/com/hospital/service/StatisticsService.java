package com.hospital.service;

import com.hospital.entity.DoctorInfo;
import com.hospital.mapper.AppointmentRecordMapper;
import com.hospital.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private static final DateTimeFormatter MONTH_DAY = DateTimeFormatter.ofPattern("MM-dd");

    @Autowired
    private AuthService authService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CardService cardService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AppointmentRecordMapper appointmentRecordMapper;

    public Map<String, Object> dashboard(Long userId, String role) {
        Map<String, Object> result = new HashMap<>();
        if ("ADMIN".equals(role)) {
            result.put("userCount", sysUserMapper.countAll());
            result.put("departmentCount", departmentService.countAll());
            result.put("doctorCount", doctorService.countAll());
            result.put("appointmentCount", appointmentService.countAll());
            result.put("totalAmount", orderService.sumPaidAmount());
            return result;
        }
        if ("DOCTOR".equals(role)) {
            DoctorInfo doctor = doctorService.getByUserId(userId);
            result.put("scheduleCount", scheduleService.countByDoctorId(doctor.getId()));
            result.put("appointmentCount", appointmentService.countByDoctorId(doctor.getId()));
            result.put("finishCount", appointmentService.countFinishedByDoctorId(doctor.getId()));
            result.put("reviewCount", reviewService.countByDoctorId(doctor.getId()));
            return result;
        }
        authService.assertPatient(role);
        result.put("appointmentCount", appointmentService.countByUserId(userId));
        result.put("paidCount", appointmentService.countPaidByUserId(userId));
        result.put("cardCount", cardService.countByUserId(userId));
        result.put("reviewCount", reviewService.countByUserId(userId));
        return result;
    }

    public List<Map<String, Object>> departmentRank(String role) {
        authService.assertAdmin(role);
        List<Map<String, Object>> rows = appointmentRecordMapper.selectDepartmentRank();
        for (Map<String, Object> row : rows) {
            Object value = row.get("rank_value");
            if (value == null) {
                value = row.get("RANK_VALUE");
            }
            if (value == null) {
                value = row.get("rankValue");
            }
            row.put("value", value);
        }
        return rows;
    }

    public List<Map<String, Object>> appointmentTrend(String role) {
        authService.assertAdmin(role);
        List<Map<String, Object>> rows = appointmentRecordMapper.selectAppointmentTrend();
        for (Map<String, Object> row : rows) {
            Object date = firstPresent(row, "trend_date", "TREND_DATE", "trendDate");
            Object count = firstPresent(row, "trend_count", "TREND_COUNT", "trendCount");
            row.put("date", formatMonthDay(date));
            row.put("count", count);
        }
        return rows;
    }

    public List<Map<String, Object>> hotDoctorRank() {
        return doctorService.hotDoctorRank();
    }

    private Object firstPresent(Map<String, Object> row, String... keys) {
        for (String key : keys) {
            Object value = row.get(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private String formatMonthDay(Object value) {
        if (value instanceof Date) {
            return ((Date) value).toLocalDate().format(MONTH_DAY);
        }
        if (value instanceof LocalDate) {
            return ((LocalDate) value).format(MONTH_DAY);
        }
        if (value == null) {
            return "";
        }
        String text = String.valueOf(value);
        return text.length() >= 10 ? text.substring(5, 10) : text;
    }
}

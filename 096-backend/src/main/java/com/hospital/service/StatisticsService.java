package com.hospital.service;

import com.hospital.entity.DoctorInfo;
import com.hospital.mapper.AppointmentRecordMapper;
import com.hospital.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

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
        return appointmentRecordMapper.selectDepartmentRank();
    }

    public List<Map<String, Object>> appointmentTrend(String role) {
        authService.assertAdmin(role);
        return appointmentRecordMapper.selectAppointmentTrend();
    }

    public List<Map<String, Object>> hotDoctorRank() {
        return doctorService.hotDoctorRank();
    }
}

package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.repository.AppointmentRepository;
import com.xiaou.seniorhealth.repository.ElderRepository;
import com.xiaou.seniorhealth.repository.FollowUpRepository;
import com.xiaou.seniorhealth.repository.SysUserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final SysUserRepository sysUserRepository;
    private final ElderRepository elderRepository;
    private final AppointmentRepository appointmentRepository;
    private final FollowUpRepository followUpRepository;
    public StatsController(SysUserRepository sysUserRepository, ElderRepository elderRepository, AppointmentRepository appointmentRepository, FollowUpRepository followUpRepository) {
        this.sysUserRepository = sysUserRepository;
        this.elderRepository = elderRepository;
        this.appointmentRepository = appointmentRepository;
        this.followUpRepository = followUpRepository;
    }
    @GetMapping("/overview")
    public ApiResponse<Map<String, Object>> overview() {
        Map<String, Object> r = new HashMap<>();
        r.put("users", sysUserRepository.count());
        r.put("elders", elderRepository.count());
        r.put("upcomingAppointments", appointmentRepository.countUpcoming(LocalDateTime.now()));
        r.put("pendingFollowUps7d", followUpRepository.countPendingInRange(LocalDate.now(), LocalDate.now().plusDays(7)));
        return ApiResponse.ok(r);
    }
}

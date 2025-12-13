package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.Appointment;
import com.xiaou.seniorhealth.dto.AppointmentCreateDTO;
import com.xiaou.seniorhealth.repository.AppointmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;
    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping
    public ApiResponse<Appointment> create(@Valid @RequestBody AppointmentCreateDTO dto) {
        Appointment a = new Appointment();
        BeanUtils.copyProperties(dto, a);
        a.setStatus("SCHEDULED");
        a.setCreatedAt(LocalDateTime.now());
        Appointment saved = appointmentRepository.save(a);
        return ApiResponse.ok(saved);
    }
    @GetMapping("/elder/{elderId}")
    public ApiResponse<List<Appointment>> byElder(@PathVariable Long elderId) {
        return ApiResponse.ok(appointmentRepository.findByElder(elderId));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/doctor/{doctorUserId}/upcoming")
    public ApiResponse<List<Appointment>> upcoming(@PathVariable Long doctorUserId) {
        return ApiResponse.ok(appointmentRepository.findUpcomingByDoctor(doctorUserId, LocalDateTime.now()));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping("/{id}/status")
    public ApiResponse<Appointment> changeStatus(@PathVariable Long id, @RequestParam String status) {
        Appointment a = appointmentRepository.findById(id).orElse(null);
        if (a == null) return ApiResponse.fail("not found");
        a.setStatus(status);
        return ApiResponse.ok(appointmentRepository.save(a));
    }
}

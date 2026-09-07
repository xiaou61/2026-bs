package com.psychology.system.controller;

import com.psychology.system.common.Result;
import com.psychology.system.entity.Appointment;
import com.psychology.system.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public Result<Appointment> createAppointment(
            @RequestHeader("userId") Long userId,
            @RequestBody Appointment appointment) {
        appointment.setUserId(userId);
        return Result.success(appointmentService.createAppointment(appointment));
    }

    @GetMapping("/my")
    public Result<List<Appointment>> getMyAppointments(@RequestHeader("userId") Long userId) {
        return Result.success(appointmentService.getMyAppointments(userId));
    }

    @GetMapping("/{id}")
    public Result<Appointment> getAppointmentById(@PathVariable Long id) {
        return Result.success(appointmentService.getAppointmentById(id));
    }
}

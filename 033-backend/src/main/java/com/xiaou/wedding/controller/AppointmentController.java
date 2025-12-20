package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.Appointment;
import com.xiaou.wedding.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/list")
    public Result<List<Appointment>> list(@RequestParam(required = false) Long customerId,
                                          @RequestParam(required = false) String date) {
        return Result.success(appointmentService.list(customerId, date));
    }

    @GetMapping("/{id}")
    public Result<Appointment> detail(@PathVariable Long id) {
        return Result.success(appointmentService.detail(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Appointment appointment) {
        appointmentService.create(appointment);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Appointment appointment) {
        appointmentService.update(appointment);
        return Result.success();
    }
}

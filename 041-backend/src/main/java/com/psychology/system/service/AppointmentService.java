package com.psychology.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psychology.system.entity.Appointment;
import com.psychology.system.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentMapper appointmentMapper;

    public Appointment createAppointment(Appointment appointment) {
        appointment.setStatus("PENDING");
        appointment.setPaymentStatus("UNPAID");
        appointmentMapper.insert(appointment);
        return appointment;
    }

    public List<Appointment> getMyAppointments(Long userId) {
        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("created_at");
        return appointmentMapper.selectList(wrapper);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentMapper.selectById(id);
    }
}

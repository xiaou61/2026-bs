package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.Appointment;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.AppointmentMapper;
import com.xiaou.wedding.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public List<Appointment> list(Long customerId, String date) {
        return appointmentMapper.list(customerId, date);
    }

    @Override
    public Appointment detail(Long id) {
        Appointment appointment = appointmentMapper.findById(id);
        if (appointment == null) {
            throw new BusinessException("Appointment not found");
        }
        return appointment;
    }

    @Override
    public void create(Appointment appointment) {
        int conflict = appointmentMapper.countConflict(
                appointment.getAppointmentDate().toString(),
                appointment.getTimeSlot(),
                appointment.getPhotographerId(),
                appointment.getStudioId()
        );
        if (conflict > 0) {
            throw new BusinessException("Time slot conflict");
        }
        appointment.setStatus(appointment.getStatus() == null ? "PENDING" : appointment.getStatus());
        appointmentMapper.insert(appointment);
    }

    @Override
    public void update(Appointment appointment) {
        if (appointmentMapper.findById(appointment.getId()) == null) {
            throw new BusinessException("Appointment not found");
        }
        appointmentMapper.update(appointment);
    }
}

package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.Appointment;
import java.util.List;

public interface AppointmentService {
    List<Appointment> list(Long customerId, String date);

    Appointment detail(Long id);

    void create(Appointment appointment);

    void update(Appointment appointment);
}

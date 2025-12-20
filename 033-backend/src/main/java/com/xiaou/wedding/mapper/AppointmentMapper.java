package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.Appointment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AppointmentMapper {
    Appointment findById(@Param("id") Long id);

    List<Appointment> list(@Param("customerId") Long customerId, @Param("date") String date);

    int countConflict(@Param("appointmentDate") String appointmentDate,
                      @Param("timeSlot") String timeSlot,
                      @Param("photographerId") Long photographerId,
                      @Param("studioId") Long studioId);

    int insert(Appointment appointment);

    int update(Appointment appointment);
}

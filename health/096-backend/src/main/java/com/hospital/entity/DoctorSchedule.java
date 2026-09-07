package com.hospital.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DoctorSchedule {
    private Long id;
    private Long doctorId;
    private Long departmentId;
    private LocalDate scheduleDate;
    private String timeSlot;
    private Integer totalSource;
    private Integer remainingSource;
    private Integer status;
    private BigDecimal fee;
    private String clinicRoom;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String doctorName;
    private String departmentName;
    private Long userId;
}

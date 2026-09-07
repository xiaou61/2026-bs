package com.xiaou.seniorhealth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentCreateDTO {
    @NotNull
    private Long elderId;
    @NotNull
    private Long doctorUserId;
    @NotNull
    private LocalDateTime startTime;
    private String reason;
}

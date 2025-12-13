package com.xiaou.seniorhealth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FollowUpCreateDTO {
    @NotNull
    private Long elderId;
    @NotNull
    private Long doctorUserId;
    @NotNull
    private String type;
    @NotNull
    private LocalDate dueDate;
    private String note;
}

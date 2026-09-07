package com.xiaou.rice.modules.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateBookingRequest {
    @NotNull
    private Long plotId;
    @NotNull
    @FutureOrPresent
    private LocalDate expectDate;
    private String timeWindow;
    private BigDecimal area;
    private String address;
    private Double latitude;
    private Double longitude;
    private String remark;
}

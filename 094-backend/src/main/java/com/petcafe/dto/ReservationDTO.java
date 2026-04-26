package com.petcafe.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {
    private Long shopId;
    private Long seatId;
    private String customerName;
    private String customerPhone;
    private LocalDate visitDate;
    private String visitTime;
    private Integer peopleCount;
    private String petCompanion;
    private String remark;
}

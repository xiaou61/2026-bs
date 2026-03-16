package com.railway.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDTO {
    private Long scheduleId;
    private List<Long> seatIds;
    private List<Long> passengerIds;
    private String contactPhone;
}

package com.railway.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeatLockDTO {
    private Long scheduleId;
    private List<Long> seatIds;
}

package com.ticket.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeatLockDTO {
    private Long showtimeId;
    private List<Long> seatIds;
}

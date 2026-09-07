package com.ticket.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDTO {
    private Long showtimeId;
    private List<Long> seatIds;
    private Long couponId;
}

package com.xiaou.ticket.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {
    private Long matchId;
    private Long pricingId;
    private List<Long> seatIds;
}

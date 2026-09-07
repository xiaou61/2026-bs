package com.xiaou.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatAvailability {
    private Long id;
    private Long categoryId;
    private String rowNumber;
    private String seatNumber;
    private String label;
    private String status;
}

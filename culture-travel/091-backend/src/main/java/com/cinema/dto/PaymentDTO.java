package com.cinema.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long orderId;
    private String payType;
}

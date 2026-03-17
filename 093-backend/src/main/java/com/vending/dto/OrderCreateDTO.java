package com.vending.dto;

import lombok.Data;

@Data
public class OrderCreateDTO {
    private Long machineId;
    private Long slotId;
    private Integer quantity;
}

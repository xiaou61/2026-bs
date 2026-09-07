package com.vending.dto;

import lombok.Data;

@Data
public class ReplenishDTO {
    private Long machineId;
    private Long slotId;
    private Integer quantity;
    private String remark;
}

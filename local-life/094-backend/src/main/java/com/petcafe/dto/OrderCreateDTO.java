package com.petcafe.dto;

import lombok.Data;

@Data
public class OrderCreateDTO {
    private Long shopId;
    private Long menuId;
    private Integer quantity;
    private String dineType;
    private String remark;
}

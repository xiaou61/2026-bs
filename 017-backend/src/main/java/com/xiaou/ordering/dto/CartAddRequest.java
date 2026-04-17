package com.xiaou.ordering.dto;

import lombok.Data;

@Data
public class CartAddRequest {
    private Long dishId;
    private Integer quantity;
}

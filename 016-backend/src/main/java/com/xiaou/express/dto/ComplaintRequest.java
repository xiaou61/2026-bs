package com.xiaou.express.dto;

import lombok.Data;

@Data
public class ComplaintRequest {
    private Long orderId;
    private String type;
    private String reason;
    private String evidence;
}


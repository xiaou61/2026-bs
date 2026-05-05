package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RectificationOrder {
    private Long id;
    private String orderNo;
    private String hazardNo;
    private String responsibleTeam;
    private String deadlineTime;
    private String rectifyRequirement;
    private String verifyStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

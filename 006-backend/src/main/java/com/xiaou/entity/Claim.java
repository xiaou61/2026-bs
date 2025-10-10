package com.xiaou.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Claim {
    private Long id;
    private String itemType;
    private Long itemId;
    private Long claimerId;
    private Long ownerId;
    private String verifyInfo;
    private String reason;
    private Integer status;
    private String reply;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private String itemTitle;
    private String claimerName;
    private String ownerName;
}


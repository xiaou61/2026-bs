package com.zerotrust.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NetworkSegment {
    private Long id;
    private String segmentName;
    private String segmentCode;
    private String cidrBlock;
    private String securityZone;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

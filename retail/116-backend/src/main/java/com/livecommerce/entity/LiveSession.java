package com.livecommerce.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LiveSession {
    private Long id;
    private String sessionNo;
    private String sessionTitle;
    private String channelName;
    private String anchorName;
    private String startTime;
    private BigDecimal targetGmv;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

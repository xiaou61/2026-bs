package com.livecommerce.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LiveChannel {
    private Long id;
    private String channelName;
    private String platformName;
    private String accountNo;
    private String ownerName;
    private Integer fansCount;
    private String contactPhone;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

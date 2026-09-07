package com.xiaou.wedding.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Photo {
    private Long id;
    private Long orderId;
    private Long customerId;
    private Long photographerId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private Integer isSelected;
    private Integer isRefined;
    private String refinedStatus;
    private LocalDateTime uploadTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleted;
}

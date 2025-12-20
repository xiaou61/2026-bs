package com.xiaou.wedding.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Package {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private String description;
    private String content;
    private Integer costumeCount;
    private Integer refinedPhotoCount;
    private Integer shootingDuration;
    private Integer makeupCount;
    private Integer sceneCount;
    private String gift;
    private String coverImage;
    private String images;
    private Integer status;
    private Integer sortOrder;
    private Integer salesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleted;
}

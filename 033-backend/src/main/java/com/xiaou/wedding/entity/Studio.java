package com.xiaou.wedding.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Studio {
    private Long id;
    private String name;
    private BigDecimal area;
    private String style;
    private String equipment;
    private String description;
    private String images;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleted;
}

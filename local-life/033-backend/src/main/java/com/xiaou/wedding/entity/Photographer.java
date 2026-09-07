package com.xiaou.wedding.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Photographer {
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private String photo;
    private String level;
    private String specialty;
    private String portfolio;
    private String introduction;
    private Integer status;
    private BigDecimal commissionRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleted;
}

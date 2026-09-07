package com.xiaou.wedding.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Costume {
    private Long id;
    private String code;
    private String name;
    private String category;
    private String size;
    private String color;
    private String images;
    private String status;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleted;
}

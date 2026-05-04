package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductCargo {
    private Long id;
    private String cargoName;
    private String cargoNo;
    private String categoryName;
    private String ownerName;
    private String temperatureRange;
    private Integer cargoWeight;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

package com.community.parking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReportDTO {
    private String plateNumber;
    private Long violationTypeId;
    private String location;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String description;
    private String images;
    private Boolean isAnonymous;
}

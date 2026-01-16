package com.community.parking.dto;

import lombok.Data;

@Data
public class ReportDTO {
    private String plateNumber;
    private Long violationTypeId;
    private String location;
    private Double longitude;
    private Double latitude;
    private String description;
    private String images;
    private Boolean isAnonymous;
}

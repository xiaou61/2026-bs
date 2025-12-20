package com.xiaou.rice.modules.farm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlotRequest {
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal area;
    private String cropVariety;
    private String growthStage;
    private String location;
    private Double latitude;
    private Double longitude;
    private String photos;
    private String remark;
}

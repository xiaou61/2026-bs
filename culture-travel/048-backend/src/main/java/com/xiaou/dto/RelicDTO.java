package com.xiaou.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RelicDTO {
    private Long id;
    private String name;
    private Long categoryId;
    private Long dynastyId;
    private Long hallId;
    private String relicNo;
    private String material;
    private String size;
    private String weight;
    private String origin;
    private LocalDate discoveryDate;
    private Integer level;
    private String image;
    private String modelUrl;
    private String audioUrl;
    private String description;
    private String historicalValue;
    private Integer status;
}

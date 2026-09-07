package com.xiaou.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ScriptDTO {
    private Long id;
    private Long categoryId;
    private String title;
    private String cover;
    private String description;
    private Integer difficulty;
    private String playerCount;
    private String duration;
    private Integer type;
    private BigDecimal price;
    private List<ScriptContentDTO> contents;
}

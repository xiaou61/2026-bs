package com.football.dto;

import lombok.Data;

@Data
public class MatchResultDTO {
    private Integer homeScore;
    private Integer awayScore;
    private String referee;
    private String remark;
}

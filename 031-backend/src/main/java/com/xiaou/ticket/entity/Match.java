package com.xiaou.ticket.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Match {
    private Long id;
    private String title;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long stadiumId;
    private LocalDateTime matchDate;
    private String matchType;
    private String league;
    private String season;
    private String description;
    private String posterUrl;
    private String status;
    private Integer homeScore;
    private Integer awayScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

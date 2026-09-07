package com.security.vo;

import lombok.Data;

@Data
public class UserStatsVO {
    private Integer learnedCount;
    private Integer questionCount;
    private Integer correctCount;
    private Integer favoriteCount;
    private Integer points;
    private Integer level;
    private Integer rank;
}

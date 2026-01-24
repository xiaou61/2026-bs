package com.security.vo;

import lombok.Data;

@Data
public class StatsVO {
    private Long userCount;
    private Long articleCount;
    private Long questionCount;
    private Long newsCount;
    private Long qaCount;
    private Long todayAnswerCount;
}

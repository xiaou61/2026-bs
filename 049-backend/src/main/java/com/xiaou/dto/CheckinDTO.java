package com.xiaou.dto;

import lombok.Data;

@Data
public class CheckinDTO {
    private Integer studyDuration;
    private Integer questionCount;
    private Integer correctCount;
    private String note;
    private Integer mood;
}

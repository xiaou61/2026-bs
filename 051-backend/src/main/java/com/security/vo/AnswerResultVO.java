package com.security.vo;

import lombok.Data;

@Data
public class AnswerResultVO {
    private Boolean isCorrect;
    private String correctAnswer;
    private String explanation;
    private Integer points;
}

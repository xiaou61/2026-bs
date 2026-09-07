package com.xiaou.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long questionId;
    private String userAnswer;
    private String answer;
    private Integer timeSpent;

    public String submittedAnswer() {
        return userAnswer != null ? userAnswer : answer;
    }
}

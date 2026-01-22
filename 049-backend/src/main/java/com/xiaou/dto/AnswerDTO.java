package com.xiaou.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long questionId;
    private String userAnswer;
    private Integer timeSpent;
}

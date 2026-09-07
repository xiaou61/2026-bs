package com.security.vo;

import lombok.Data;
import java.util.List;

@Data
public class QuestionVO {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String content;
    private List<String> options;
    private Integer difficulty;
}

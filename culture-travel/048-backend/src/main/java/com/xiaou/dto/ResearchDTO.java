package com.xiaou.dto;

import lombok.Data;

@Data
public class ResearchDTO {
    private Long id;
    private String title;
    private Long relicId;
    private String content;
    private String summary;
    private String fileUrl;
}

package com.knowledgeqa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentChunk {
    private Long id;
    private Long documentId;
    private String chunkTitle;
    private String chunkContent;
    private String keywords;
    private Integer vectorStatus;
    private Integer sort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

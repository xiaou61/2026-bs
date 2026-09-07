package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialDownload {
    private Long id;
    private Long materialId;
    private Long userId;
    private String source;
    private LocalDateTime createTime;
}

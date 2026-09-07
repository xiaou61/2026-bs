package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialFile {
    private Long id;
    private Long materialId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private LocalDateTime createTime;
}

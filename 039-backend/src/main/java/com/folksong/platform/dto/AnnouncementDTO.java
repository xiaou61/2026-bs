package com.folksong.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnnouncementDTO {
    private Long id;
    
    @NotBlank(message = "公告标题不能为空")
    private String title;
    
    @NotBlank(message = "公告内容不能为空")
    private String content;
    
    private Integer status;
}

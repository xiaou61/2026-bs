package com.folksong.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FolkSongDTO {
    private Long id;
    
    @NotBlank(message = "标题不能为空")
    private String title;
    
    @NotNull(message = "分类不能为空")
    private Long categoryId;
    
    private String content;
    private String lyrics;
    private String audioUrl;
    private String videoUrl;
    private String coverImage;
    private String region;
    private String ethnic;
    private String introduction;
}

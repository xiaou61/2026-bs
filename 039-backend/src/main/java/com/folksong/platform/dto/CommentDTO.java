package com.folksong.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {
    @NotNull(message = "民歌ID不能为空")
    private Long songId;
    
    private Long parentId;
    
    @NotBlank(message = "评论内容不能为空")
    private String content;
}

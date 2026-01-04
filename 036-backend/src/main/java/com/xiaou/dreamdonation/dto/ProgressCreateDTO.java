package com.xiaou.dreamdonation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgressCreateDTO {
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @NotBlank(message = "进度标题不能为空")
    private String title;

    @NotBlank(message = "进度内容不能为空")
    private String content;

    private String images;
}

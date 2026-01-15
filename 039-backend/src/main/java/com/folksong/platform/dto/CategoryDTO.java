package com.folksong.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    
    @NotBlank(message = "分类名称不能为空")
    private String name;
    
    private String description;
    private String region;
    private String coverImage;
    private Integer sortOrder;
    private Integer status;
}

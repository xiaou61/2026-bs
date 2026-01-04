package com.xiaou.dreamdonation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProjectCreateDTO {
    @NotBlank(message = "项目标题不能为空")
    private String title;

    @NotBlank(message = "项目描述不能为空")
    private String description;

    private String coverImage;

    @NotBlank(message = "项目分类不能为空")
    private String category;

    @NotNull(message = "目标金额不能为空")
    private BigDecimal targetAmount;

    @NotNull(message = "开始日期不能为空")
    private LocalDateTime startDate;

    @NotNull(message = "结束日期不能为空")
    private LocalDateTime endDate;

    private String organizationName;
    private String location;
}

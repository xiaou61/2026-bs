package com.xiaou.health.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConsultationCreateRequest {
    @NotNull(message = "医生ID不能为空")
    private Long doctorId;
    
    @NotBlank(message = "标题不能为空")
    private String title;
    
    @NotBlank(message = "问题不能为空")
    private String question;
}

package com.xiaou.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PetDTO {
    private Long id;
    @NotBlank(message = "宠物名称不能为空")
    private String name;
    @NotBlank(message = "宠物类型不能为空")
    private String type;
    private String breed;
    @NotNull(message = "年龄不能为空")
    private Integer age;
    private String gender;
    private BigDecimal weight;
    private String avatar;
    private String healthStatus;
    private String vaccination;
    private String remark;
}

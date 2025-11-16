package com.xiaou.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductPublishDTO {

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @NotBlank(message = "商品标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100字符")
    private String title;

    @NotBlank(message = "商品描述不能为空")
    @Size(max = 1000, message = "描述长度不能超过1000字符")
    private String description;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    @Digits(integer = 8, fraction = 2, message = "价格格式不正确")
    private BigDecimal price;

    private BigDecimal originalPrice;

    @NotBlank(message = "成色不能为空")
    @Pattern(regexp = "^(全新|九成新|八成新|七成新)$", message = "成色必须是:全新/九成新/八成新/七成新")
    private String condition;

    private List<String> images;
}
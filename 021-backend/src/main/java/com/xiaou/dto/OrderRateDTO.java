package com.xiaou.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRateDTO {

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最少为1分")
    @Max(value = 5, message = "评分最高为5分")
    private Integer rating;

    @Size(max = 500, message = "评价内容不能超过500字符")
    private String comment;
}

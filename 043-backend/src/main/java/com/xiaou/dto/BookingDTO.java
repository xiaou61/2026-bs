package com.xiaou.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingDTO {
    private Long id;
    @NotNull(message = "宠物ID不能为空")
    private Long petId;
    @NotNull(message = "服务商ID不能为空")
    private Long providerId;
    @NotNull(message = "服务ID不能为空")
    private Long serviceId;
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;
    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;
    private BigDecimal totalPrice;
    private String remark;
}

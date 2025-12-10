package com.xiaou.bike.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 还车DTO
 */
@Data
public class ReturnBikeDTO {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    /**
     * 还车停车点ID
     */
    @NotNull(message = "停车点不能为空")
    private Long stationId;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 还车照片
     */
    private String returnPhoto;
}

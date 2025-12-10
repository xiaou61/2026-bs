package com.xiaou.bike.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 租车DTO
 */
@Data
public class RentBikeDTO {

    /**
     * 车辆编号或二维码
     */
    @NotBlank(message = "车辆编号不能为空")
    private String bikeNo;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
}

package com.xiaou.bike.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 故障上报DTO
 */
@Data
public class FaultReportDTO {

    /**
     * 车辆ID
     */
    @NotNull(message = "车辆ID不能为空")
    private Long bikeId;

    /**
     * 故障类型：1刹车故障 2车铃故障 3轮胎故障 4车锁故障 5座椅故障 6其他
     */
    @NotNull(message = "故障类型不能为空")
    private Integer faultType;

    /**
     * 故障描述
     */
    private String description;

    /**
     * 故障图片
     */
    private String images;
}

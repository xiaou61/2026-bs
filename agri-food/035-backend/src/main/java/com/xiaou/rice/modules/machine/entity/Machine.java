package com.xiaou.rice.modules.machine.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaou.rice.common.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("machine")
public class Machine extends BaseEntity {
    private Long ownerId; // 机手/合作社用户ID
    private String name;
    private String model;
    private BigDecimal width; // 割幅(米)
    private BigDecimal power; // 功率/马力
    private BigDecimal pricePerHour;
    private BigDecimal pricePerMu;
    private Integer serviceRadiusKm;
    /** 0=停用 1=可用 2=维修 */
    private Integer status;
    private String remark;
}

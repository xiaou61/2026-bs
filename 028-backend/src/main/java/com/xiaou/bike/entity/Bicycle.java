package com.xiaou.bike.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 自行车实体类
 */
@Data
@TableName("bicycle")
public class Bicycle {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车辆编号
     */
    private String bikeNo;

    /**
     * 二维码内容
     */
    private String qrCode;

    /**
     * 车辆类型：1普通单车 2电动助力车
     */
    private Integer bikeType;

    /**
     * 状态：0空闲 1使用中 2维修中 3报废
     */
    private Integer status;

    /**
     * 当前停车点ID
     */
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
     * 总里程（km）
     */
    private BigDecimal totalMileage;

    /**
     * 总订单数
     */
    private Integer totalOrders;

    /**
     * 上次维护时间
     */
    private LocalDateTime lastMaintenance;

    /**
     * 购入日期
     */
    private LocalDate purchaseDate;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

package com.xiaou.bike.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 租借订单实体类
 */
@Data
@TableName("rental_order")
public class RentalOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 车辆ID
     */
    private Long bikeId;

    /**
     * 起始停车点ID
     */
    private Long startStationId;

    /**
     * 结束停车点ID
     */
    private Long endStationId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 骑行时长（分钟）
     */
    private Integer duration;

    /**
     * 骑行距离（km）
     */
    private BigDecimal distance;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 状态：0进行中 1已完成 2异常 3已取消
     */
    private Integer status;

    /**
     * 支付状态：0未支付 1已支付
     */
    private Integer payStatus;

    /**
     * 还车照片
     */
    private String returnPhoto;

    /**
     * 备注
     */
    private String remark;

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

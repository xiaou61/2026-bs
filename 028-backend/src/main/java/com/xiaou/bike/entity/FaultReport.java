package com.xiaou.bike.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 故障上报实体类
 */
@Data
@TableName("fault_report")
public class FaultReport {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车辆ID
     */
    private Long bikeId;

    /**
     * 上报用户ID
     */
    private Long userId;

    /**
     * 故障类型：1刹车故障 2车铃故障 3轮胎故障 4车锁故障 5座椅故障 6其他
     */
    private Integer faultType;

    /**
     * 故障描述
     */
    private String description;

    /**
     * 故障图片（多个逗号分隔）
     */
    private String images;

    /**
     * 状态：0待处理 1处理中 2已完成 3已关闭
     */
    private Integer status;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理结果
     */
    private String handleResult;

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

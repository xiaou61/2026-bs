package com.xiaou.bike.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 信用记录实体类
 */
@Data
@TableName("credit_record")
public class CreditRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 变动值
     */
    private Integer changeValue;

    /**
     * 变动类型：1正常还车 2规范停车 3上报故障 4超时未还 5违规停车 6损坏车辆 7管理员调整
     */
    private Integer changeType;

    /**
     * 变动原因
     */
    private String reason;

    /**
     * 关联订单ID
     */
    private Long orderId;

    /**
     * 变动后信用分
     */
    private Integer scoreAfter;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

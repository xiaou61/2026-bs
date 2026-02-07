package com.milk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("delivery_record")
public class DeliveryRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long deliveryUserId;
    private Long routeId;
    private Integer status;
    private String remark;
    private LocalDateTime deliveryTime;
    private LocalDateTime createTime;
}

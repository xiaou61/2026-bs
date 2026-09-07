package com.milk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("delivery_route")
public class DeliveryRoute {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long areaId;
    private Long deliveryUserId;
    private String description;
    private Integer status;
}

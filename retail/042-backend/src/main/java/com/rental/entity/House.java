package com.rental.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 房源实体
 */
@Data
@TableName("house")
public class House {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long landlordId;

    private String title;

    private String description;

    private String province;

    private String city;

    private String district;

    private String address;

    private BigDecimal price;

    private BigDecimal deposit;

    private BigDecimal area;

    private String roomType;

    private Integer floor;

    private Integer totalFloor;

    private String orientation;

    private String decoration;

    private String facilities;

    private String images;

    private Integer status;

    private Integer viewCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}

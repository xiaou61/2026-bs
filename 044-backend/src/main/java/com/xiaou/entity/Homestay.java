package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("homestay")
public class Homestay {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long hostId;
    private String name;
    private String description;
    private String province;
    private String city;
    private String district;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String coverImage;
    private String images;
    private BigDecimal minPrice;
    private Integer maxGuests;
    private BigDecimal rating;
    private Integer reviewCount;
    private Integer bookingCount;
    private Integer status;  // 0-下架 1-上架 2-审核中
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

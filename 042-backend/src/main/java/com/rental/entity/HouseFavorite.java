package com.rental.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 房源收藏实体
 */
@Data
@TableName("house_favorite")
public class HouseFavorite {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long houseId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

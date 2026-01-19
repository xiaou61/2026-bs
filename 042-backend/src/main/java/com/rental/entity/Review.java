package com.rental.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评价实体
 */
@Data
@TableName("review")
public class Review {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long houseId;

    private Long contractId;

    private Long tenantId;

    private Long landlordId;

    private Integer houseScore;

    private Integer serviceScore;

    private Integer facilityScore;

    private String content;

    private String images;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

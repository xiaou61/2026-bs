package com.rental.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 报修实体
 */
@Data
@TableName("repair")
public class Repair {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long houseId;

    private Long contractId;

    private Long tenantId;

    private Long landlordId;

    private String type;

    private String description;

    private String images;

    private Integer status;

    private String result;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

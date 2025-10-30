package com.xiaou.sport.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("sport_record")
public class SportRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String sportType;

    private Integer duration;

    private BigDecimal distance;

    private Integer calories;

    private Integer steps;

    private BigDecimal avgSpeed;

    private Integer pointsEarned;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sportDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}

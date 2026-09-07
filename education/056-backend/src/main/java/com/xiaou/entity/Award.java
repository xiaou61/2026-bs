package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("award")
public class Award {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long competitionId;
    private Long workId;
    private Long userId;
    private String awardLevel;
    private String certificate;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String workTitle;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private BigDecimal score;
    @TableField(exist = false)
    private Integer rank;
}

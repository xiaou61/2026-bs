package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("script")
public class Script {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long authorId;
    private Long categoryId;
    private String title;
    private String cover;
    private String description;
    private Integer difficulty; // 1-新手 2-进阶 3-硬核
    private String playerCount;
    private String duration;
    private Integer type; // 1-情感 2-推理 3-恐怖 4-欢乐 5-机制
    private BigDecimal price;
    private Integer viewCount;
    private Integer likeCount;
    private Integer status; // 0-待审核 1-已上架 2-已下架 3-审核拒绝
    private String auditRemark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}

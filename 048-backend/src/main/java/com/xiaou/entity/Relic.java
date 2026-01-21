package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("relic")
public class Relic {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long categoryId;
    private Long dynastyId;
    private Long hallId;
    private String relicNo;
    private String material;
    private String size;
    private String weight;
    private String origin;
    private LocalDate discoveryDate;
    private Integer level; // 1-一级,2-二级,3-三级,4-一般
    private String image;
    private String modelUrl;
    private String audioUrl;
    private String description;
    private String historicalValue;
    private Integer viewCount;
    private Integer likeCount;
    private Integer status; // 0-下架,1-展出中,2-修复中,3-外借中
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private String dynastyName;
    @TableField(exist = false)
    private String hallName;
}

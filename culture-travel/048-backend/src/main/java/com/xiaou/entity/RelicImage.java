package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("relic_image")
public class RelicImage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long relicId;
    private String imageUrl;
    private String description;
    private Integer sort;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

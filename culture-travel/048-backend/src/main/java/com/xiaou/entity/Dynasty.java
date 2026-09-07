package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("dynasty")
public class Dynasty {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String startYear;
    private String endYear;
    private String description;
    private Integer sort;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("bed")
public class Bed {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long roomId;
    private String bedNo;
    private Integer status;  // 0-空闲 1-已入住 2-预留
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}

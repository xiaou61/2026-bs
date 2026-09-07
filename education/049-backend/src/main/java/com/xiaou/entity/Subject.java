package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("subject")
public class Subject {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String icon;
    private Integer sortOrder;
    private Integer status;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}

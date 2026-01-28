package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("document")
public class Document {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String path;
    private Long size;
    private String type;
    private String category;
    private Long uploaderId;
    private Integer isShared;
    private Integer downloadCount;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String uploaderName;
}

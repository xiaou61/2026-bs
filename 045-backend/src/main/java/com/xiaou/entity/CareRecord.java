package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("care_record")
public class CareRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long elderId;
    private Long carePlanId;
    private String careType;
    private String careContent;
    private LocalDateTime careTime;
    private Long caregiverId;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}

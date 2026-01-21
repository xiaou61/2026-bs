package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("notice")
public class Notice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Integer type; // 1-系统公告,2-展览通知,3-闭馆通知
    private LocalDateTime publishTime;
    private Integer status; // 0-草稿,1-已发布
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

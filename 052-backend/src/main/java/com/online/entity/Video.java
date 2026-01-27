package com.online.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("video")
public class Video {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long chapterId;
    private Long courseId;
    private String title;
    private String url;
    private Integer duration;
    private Integer sort;
    private Integer isFree;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("chapter")
public class Chapter {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private String title;
    private String videoUrl;
    private Integer duration;
    private Integer sortOrder;
    private Integer isFree;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}

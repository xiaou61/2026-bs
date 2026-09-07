package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("research")
public class Research {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Long relicId;
    private Long authorId;
    private String content;
    private String summary;
    private LocalDate publishDate;
    private String fileUrl;
    private Integer viewCount;
    private Integer status; // 0-待审核,1-已发布,2-已拒绝
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private String relicName;
    @TableField(exist = false)
    private String authorName;
}

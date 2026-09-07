package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("question_category")
public class QuestionCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long subjectId;
    private String name;
    private Long parentId;
    private Integer sortOrder;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String subjectName;
}

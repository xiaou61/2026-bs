package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("question")
public class Question {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private Long subjectId;
    private Integer type; // 1-单选,2-多选,3-判断,4-填空,5-简答
    private Integer difficulty; // 1-简单,2-中等,3-困难
    private String content;
    private String options;
    private String answer;
    private String analysis;
    private String source;
    private Integer viewCount;
    private Integer errorCount;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private String subjectName;
}

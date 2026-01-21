package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("script_content")
public class ScriptContent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long scriptId;
    private String chapterName;
    private String roleName;
    private String content;
    private Integer sort;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}

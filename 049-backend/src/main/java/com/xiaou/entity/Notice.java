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
    private Integer type; // 1-系统公告,2-考试通知,3-学习提醒
    private Integer isTop;
    private Integer status;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}

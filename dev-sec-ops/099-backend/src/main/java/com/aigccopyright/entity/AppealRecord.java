package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("appeal_record")
public class AppealRecord {
    private Long id;
    private String targetType;
    private Long targetId;
    private Long userId;
    private String reason;
    private Integer status;
    private String handleComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

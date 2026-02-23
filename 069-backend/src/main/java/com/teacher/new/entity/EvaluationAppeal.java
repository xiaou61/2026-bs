package com.teacher.new.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("evaluation_appeal")
public class EvaluationAppeal {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recordId;
    private Long taskId;
    private Long teacherId;
    private String reasonText;
    private String replyText;
    private String status;
    private Long handleBy;
    private LocalDateTime handleTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

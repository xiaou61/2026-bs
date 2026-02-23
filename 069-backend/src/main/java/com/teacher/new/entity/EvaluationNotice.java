package com.teacher.new.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("evaluation_notice")
public class EvaluationNotice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String contentText;
    private Integer status;
    private LocalDateTime publishTime;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

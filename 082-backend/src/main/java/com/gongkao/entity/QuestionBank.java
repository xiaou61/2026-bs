package com.gongkao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("question_bank")
public class QuestionBank {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long subjectId;
    private String name;
    private String type;
    private Integer totalCount;
    private String difficulty;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}


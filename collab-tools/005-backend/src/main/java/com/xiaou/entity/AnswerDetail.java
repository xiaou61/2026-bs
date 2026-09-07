package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("answer_detail")
public class AnswerDetail {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long answerId;
    
    private Long questionId;
    
    private String value;
}


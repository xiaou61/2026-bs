package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("consultation_answer")
public class ConsultationAnswer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long consultationId;
    private String content;
    private String images;
    private Long answererId;
    private Integer isAdopted;
    private Integer likeCount;
    private LocalDateTime createTime;
}

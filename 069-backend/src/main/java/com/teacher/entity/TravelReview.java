package com.teacher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("travel_review")
public class TravelReview {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long userId;
    private Long spotId;
    private Integer score;
    private String content;
    private Integer status;
    private String replyContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}



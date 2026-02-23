package com.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long movieId;
    private Long userId;
    private Long orderId;
    private Integer rating;
    private String content;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

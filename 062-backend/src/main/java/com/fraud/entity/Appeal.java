package com.fraud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("appeal")
public class Appeal {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long alertId;
    private Long userId;
    private String content;
    private Integer status;
    private String reply;
    private Long replyAdminId;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;
}

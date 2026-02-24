package com.autorepair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("complaint")
public class Complaint {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long userId;
    private Long targetUserId;
    private Integer orderStatusSnapshot;
    private String type;
    private String content;
    private String images;
    private Integer status;
    private String reply;
    private Long replyAdminId;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;
}



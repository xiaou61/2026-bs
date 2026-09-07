package com.petcafe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("review_record")
public class ReviewRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long shopId;
    private Long userId;
    private Integer rating;
    private String content;
    private Long replyUserId;
    private String replyContent;
    private String reviewStatus;
    private LocalDateTime reviewTime;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String orderNo;
    @TableField(exist = false)
    private String shopName;
    @TableField(exist = false)
    private String replyName;
}

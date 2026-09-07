package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("review")
public class Review {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long reservationId;
    private Long shopId;
    private Long scriptId;
    private Integer rating;
    private String content;
    private String images;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}

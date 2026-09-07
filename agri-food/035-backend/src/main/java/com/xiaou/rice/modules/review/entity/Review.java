package com.xiaou.rice.modules.review.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaou.rice.common.model.BaseEntity;
import lombok.Data;

@Data
@TableName("review")
public class Review extends BaseEntity {
    private Long bookingId;
    private Integer rating; // 1-5
    private String content;
}

package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("coupon_activity")
public class CouponActivity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String activityNo;
    private String activityName;
    private String merchantName;
    private String couponName;
    private String startDate;
    private String endDate;
    private Integer issueTotal;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

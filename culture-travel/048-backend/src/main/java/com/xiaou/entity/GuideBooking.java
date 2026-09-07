package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("guide_booking")
public class GuideBooking {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long guideId;
    private LocalDate visitDate;
    private LocalTime startTime;
    private Integer duration;
    private Integer visitorCount;
    private String language;
    private BigDecimal price;
    private Integer status; // 0-待确认,1-已确认,2-进行中,3-已完成,4-已取消
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String guideName;
}

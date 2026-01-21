package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("reservation")
public class Reservation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long exhibitionId;
    private LocalDate visitDate;
    private String timeSlot;
    private Integer visitorCount;
    private String contactName;
    private String contactPhone;
    private BigDecimal totalPrice;
    private Integer status; // 0-待确认,1-已确认,2-已完成,3-已取消
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(exist = false)
    private String exhibitionTitle;
    @TableField(exist = false)
    private String username;
}

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
    private Long shopId;
    private Long roomId;
    private Long scriptId;
    private LocalDate reservationDate;
    private String timeSlot;
    private Integer playerCount;
    private String contactName;
    private String contactPhone;
    private BigDecimal totalPrice;
    private String remark;
    private Integer status; // 0-待确认 1-已确认 2-进行中 3-已完成 4-已取消
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}

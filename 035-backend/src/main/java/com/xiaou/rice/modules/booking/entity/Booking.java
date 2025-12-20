package com.xiaou.rice.modules.booking.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaou.rice.common.model.BaseEntity;
import com.xiaou.rice.modules.booking.enums.BookingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@TableName("booking")
public class Booking extends BaseEntity {
    private Long farmerId;
    private Long machineId;
    private Long plotId;
    private LocalDate expectDate;
    private String timeWindow;
    private BigDecimal area;
    private BookingStatus status;
    private BigDecimal quoteAmount;
    private BigDecimal settleAmount;
    private String address;
    private Double latitude;
    private Double longitude;
    private String remark;

    @TableField(exist = false)
    private String plotName;
}

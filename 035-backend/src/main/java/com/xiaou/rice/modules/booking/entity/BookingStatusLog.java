package com.xiaou.rice.modules.booking.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaou.rice.common.model.BaseEntity;
import com.xiaou.rice.modules.booking.enums.BookingStatus;
import lombok.Data;

@Data
@TableName("booking_status_log")
public class BookingStatusLog extends BaseEntity {
    private Long bookingId;
    private BookingStatus status;
    private String note;
}

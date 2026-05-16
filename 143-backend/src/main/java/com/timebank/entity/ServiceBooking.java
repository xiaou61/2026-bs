package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("service_booking")
public class ServiceBooking {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String bookingNo;
    private String projectName;
    private String reserverName;
    private String bookingTime;
    private Integer bookingCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


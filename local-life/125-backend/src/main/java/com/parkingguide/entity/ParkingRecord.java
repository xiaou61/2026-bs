package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("parking_record")
public class ParkingRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String reservationNo;
    private String plateNo;
    private String enterTime;
    private String leaveTime;
    private Integer parkingMinute;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

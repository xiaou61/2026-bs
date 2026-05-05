package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("parking_lot")
public class ParkingLot {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String lotNo;
    private String lotName;
    private String cityName;
    private String addressName;
    private Integer totalSpace;
    private String operatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

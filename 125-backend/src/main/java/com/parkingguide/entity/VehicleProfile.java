package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("vehicle_profile")
public class VehicleProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String vehicleNo;
    private String ownerName;
    private String plateNo;
    private String vehicleType;
    private String phone;
    private String memberLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

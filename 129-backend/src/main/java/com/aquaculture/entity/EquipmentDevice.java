package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("equipment_device")
public class EquipmentDevice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String deviceNo;
    private String pondNo;
    private String deviceName;
    private String deviceType;
    private String installPosition;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

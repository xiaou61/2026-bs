package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("equipment_asset")
public class EquipmentAsset {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String equipmentNo;
    private String equipmentName;
    private String lineName;
    private String assetLevel;
    private String onlineDate;
    private Integer runtimeHours;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

package com.equipmentshare.entity;

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
    private String assetNo;
    private String assetName;
    private String assetModel;
    private String laboratoryName;
    private String storageTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}









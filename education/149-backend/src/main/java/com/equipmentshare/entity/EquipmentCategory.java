package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("equipment_category")
public class EquipmentCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String categoryNo;
    private String categoryName;
    private String equipmentType;
    private String storageArea;
    private String statusTag;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}









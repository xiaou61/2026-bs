package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("spare_part_catalog")
public class SparePartCatalog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String partNo;
    private String partName;
    private String categoryName;
    private String specModel;
    private String fitEquipment;
    private Integer safeStock;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

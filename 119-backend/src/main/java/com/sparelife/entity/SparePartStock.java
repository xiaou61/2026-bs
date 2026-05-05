package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("spare_part_stock")
public class SparePartStock {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String stockNo;
    private String partName;
    private String warehouseName;
    private String batchNo;
    private Integer availableQty;
    private Integer frozenQty;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

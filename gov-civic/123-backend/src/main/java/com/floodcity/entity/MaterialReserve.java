package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("material_reserve")
public class MaterialReserve {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String materialNo;
    private String materialName;
    private String materialType;
    private Integer stockQty;
    private Integer safeQty;
    private String warehouseName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

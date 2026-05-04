package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("input_material")
public class InputMaterial {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String materialName;
    private String materialNo;
    private String batchNo;
    private String supplierName;
    private Integer useAmount;
    private String auditStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("product_batch")
public class ProductBatch {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String batchNo;
    private String productName;
    private String baseName;
    private String farmerName;
    private String harvestDate;
    private String traceCode;
    private String chainStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

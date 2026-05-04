package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("product_category")
public class ProductCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String categoryName;
    private String categoryCode;
    private String standardName;
    private String inspectionRule;
    private String ownerName;
    private String riskLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

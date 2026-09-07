package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("drug_catalog")
public class DrugCatalog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String drugNo;
    private String drugName;
    private String dosageForm;
    private String manufacturerName;
    private String approvalNo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

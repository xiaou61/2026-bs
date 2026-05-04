package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("data_set_catalog")
public class DataSetCatalog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String datasetName;
    private String datasetCode;
    private String sourceName;
    private String tableName;
    private String businessDomain;
    private String securityLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

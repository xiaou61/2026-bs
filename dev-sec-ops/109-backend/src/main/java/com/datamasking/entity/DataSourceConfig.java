package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("data_source_config")
public class DataSourceConfig {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sourceName;
    private String sourceCode;
    private String sourceType;
    private String hostAddress;
    private String databaseName;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

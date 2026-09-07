package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("field_lineage")
public class FieldLineage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fieldName;
    private String datasetName;
    private String sourcePath;
    private String targetPath;
    private String ownerName;
    private String securityLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

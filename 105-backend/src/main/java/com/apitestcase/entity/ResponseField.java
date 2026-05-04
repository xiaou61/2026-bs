package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("response_field")
public class ResponseField {
    @TableId(type = IdType.AUTO)
private Long id;
private Long endpointId;
private String fieldName;
private String dataType;
private String parentPath;
private String exampleValue;
private String description;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}

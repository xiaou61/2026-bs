package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("request_param")
public class RequestParam {
    @TableId(type = IdType.AUTO)
private Long id;
private Long endpointId;
private String paramName;
private String paramType;
private String dataType;
private Integer requiredFlag;
private String exampleValue;
private String description;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}

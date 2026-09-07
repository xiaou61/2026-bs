package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("environment_config")
public class EnvironmentConfig {
    @TableId(type = IdType.AUTO)
private Long id;
private Long projectId;
private String envName;
private String baseUrl;
private String headers;
private String variables;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}

package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("api_endpoint")
public class ApiEndpoint {
    @TableId(type = IdType.AUTO)
private Long id;
private Long projectId;
private Long groupId;
private String apiName;
private String method;
private String pathUrl;
private String contentType;
private String ownerName;
private String status;
private String description;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}

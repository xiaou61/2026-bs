package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("api_project")
public class ApiProject {
    @TableId(type = IdType.AUTO)
private Long id;
private String projectName;
private String projectCode;
private String ownerName;
private String baseUrl;
private String authType;
private String versionNo;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}

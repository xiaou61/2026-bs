package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("api_group")
public class ApiGroup {
    @TableId(type = IdType.AUTO)
private Long id;
private Long projectId;
private String groupName;
private String parentName;
private Integer sortNo;
private String description;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}

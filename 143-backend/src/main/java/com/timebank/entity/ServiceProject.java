package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("service_project")
public class ServiceProject {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String projectNo;
    private String projectName;
    private String serviceTopic;
    private String serviceTarget;
    private String publishTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


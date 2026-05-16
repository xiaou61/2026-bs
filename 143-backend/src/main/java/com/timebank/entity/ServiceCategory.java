package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("service_category")
public class ServiceCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String categoryNo;
    private String categoryName;
    private String serviceType;
    private String targetGroup;
    private Integer suggestedDuration;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


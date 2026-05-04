package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("department_group")
public class DepartmentGroup {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String groupName;
    private String groupCode;
    private String businessLine;
    private String leaderName;
    private Integer employeeCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

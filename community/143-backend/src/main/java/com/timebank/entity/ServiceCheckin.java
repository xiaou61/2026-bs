package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("service_checkin")
public class ServiceCheckin {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String checkinNo;
    private String projectName;
    private String checkinName;
    private String checkinTime;
    private String serviceResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


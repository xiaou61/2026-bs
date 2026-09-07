package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("officer_profile")
public class OfficerProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String officerNo;
    private String officerName;
    private String dutyArea;
    private String contactPhone;
    private String entryTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







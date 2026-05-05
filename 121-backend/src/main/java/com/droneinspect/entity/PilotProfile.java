package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("pilot_profile")
public class PilotProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String pilotNo;
    private String pilotName;
    private String licenseLevel;
    private String teamName;
    private Integer totalHours;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("volunteer_profile")
public class VolunteerProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String volunteerNo;
    private String volunteerName;
    private String serviceExpertise;
    private String availableTime;
    private String joinTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


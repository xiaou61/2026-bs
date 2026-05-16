package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("resident_profile")
public class ResidentProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String residentNo;
    private String residentName;
    private String communityName;
    private String servicePreference;
    private String phone;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


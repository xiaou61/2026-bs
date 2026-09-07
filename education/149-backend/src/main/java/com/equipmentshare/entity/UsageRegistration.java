package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("usage_registration")
public class UsageRegistration {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String registrationNo;
    private String equipmentName;
    private String usageScene;
    private String registrationTime;
    private String teacherName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}









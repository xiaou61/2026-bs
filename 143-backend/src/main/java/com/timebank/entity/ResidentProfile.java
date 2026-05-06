package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("lab_room")
public class ResidentProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String labNo;
    private String labName;
    private String buildingName;
    private String managerName;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






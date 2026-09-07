package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("farmer_profile")
public class FarmerProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String farmerName;
    private String farmerNo;
    private String cooperativeName;
    private String regionName;
    private String phone;
    private String creditLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

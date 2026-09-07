package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("repair_record")
public class RepairRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String repairNo;
    private String assetName;
    private String faultLocation;
    private Integer repairHours;
    private String contactName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

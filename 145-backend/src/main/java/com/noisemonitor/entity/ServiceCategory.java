package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("supplier_profile")
public class MonitoringSite {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String supplierNo;
    private String supplierName;
    private String contactName;
    private String phoneNumber;
    private String qualificationLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







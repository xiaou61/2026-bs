package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("violation_record")
public class ViolationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String violationNo;
    private String equipmentName;
    private String handlerName;
    private String violationType;
    private String violationTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}









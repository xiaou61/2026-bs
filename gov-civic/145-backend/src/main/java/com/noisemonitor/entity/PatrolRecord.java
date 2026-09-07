package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("patrol_record")
public class PatrolRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String patrolNo;
    private String complaintTitle;
    private String patrolOfficer;
    private String patrolTime;
    private String patrolResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







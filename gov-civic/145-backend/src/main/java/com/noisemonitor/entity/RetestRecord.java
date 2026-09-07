package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("retest_record")
public class RetestRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String retestNo;
    private String complaintTitle;
    private String retestOfficer;
    private BigDecimal noiseDbValue;
    private String retestTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







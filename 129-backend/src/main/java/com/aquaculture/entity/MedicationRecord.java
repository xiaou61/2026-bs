package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("medication_record")
public class MedicationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String medNo;
    private String pondNo;
    private String medicineName;
    private BigDecimal useAmount;
    private String useDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

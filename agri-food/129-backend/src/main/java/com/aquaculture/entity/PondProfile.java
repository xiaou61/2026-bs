package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("pond_profile")
public class PondProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String pondNo;
    private String pondName;
    private String farmArea;
    private BigDecimal waterArea;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

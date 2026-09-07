package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("risk_warning")
public class RiskWarning {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String warningNo;
    private String equipmentName;
    private String partName;
    private String warningLevel;
    private String reasonText;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

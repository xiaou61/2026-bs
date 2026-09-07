package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("regulation_check")
public class RegulationCheck {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String checkNo;
    private String targetName;
    private String checkType;
    private String regulatorName;
    private String checkDate;
    private String resultStatus;
    private String rectifyText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

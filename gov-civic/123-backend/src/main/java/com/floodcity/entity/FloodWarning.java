package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("flood_warning")
public class FloodWarning {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String warningNo;
    private String pointName;
    private String warningLevel;
    private String triggerReason;
    private String handlerName;
    private String triggerTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

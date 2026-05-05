package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("drainage_pump")
public class DrainagePump {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String pumpNo;
    private String pumpName;
    private String districtName;
    private BigDecimal capacityValue;
    private Integer onlineCount;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("time_exchange")
public class TimeExchange {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String exchangeNo;
    private String projectName;
    private String exchangerName;
    private Integer exchangeHours;
    private String exchangeTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


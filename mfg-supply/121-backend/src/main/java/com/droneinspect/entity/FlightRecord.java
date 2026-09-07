package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("flight_record")
public class FlightRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String taskNo;
    private String droneName;
    private String startTime;
    private Integer flightDuration;
    private String resultType;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

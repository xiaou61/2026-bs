package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("rain_gauge_station")
public class RainGaugeStation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String stationNo;
    private String stationName;
    private String districtName;
    private String deviceType;
    private Integer sampleMinute;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

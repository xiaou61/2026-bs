package com.xiaou.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateTaskDTO {
    private Long courseId;
    private Long scheduleId;
    private String title;
    private Integer signType; // 1-普通 2-定位 3-二维码 4-数字
    private Integer duration; // 签到时长（分钟）
    private Integer lateMinutes; // 迟到时间界限（分钟）
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private Integer distance; // 签到范围（米）
}

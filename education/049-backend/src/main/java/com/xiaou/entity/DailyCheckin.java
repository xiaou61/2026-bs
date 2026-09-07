package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("daily_checkin")
public class DailyCheckin {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private LocalDate checkinDate;
    private Integer studyDuration; // 学习时长(分钟)
    private Integer questionCount;
    private Integer correctCount;
    private String note;
    private Integer mood; // 1-5
    private LocalDateTime createTime;
}

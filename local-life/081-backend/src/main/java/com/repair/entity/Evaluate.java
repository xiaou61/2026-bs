package com.repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("evaluate")
public class Evaluate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long userId;
    private Long technicianId;
    private Integer score;
    private Integer attitudeScore;
    private Integer qualityScore;
    private Integer speedScore;
    private String content;
    private String images;
    private LocalDateTime createTime;
}

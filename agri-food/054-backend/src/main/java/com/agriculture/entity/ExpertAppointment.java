package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("expert_appointment")
public class ExpertAppointment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long expertId;
    private Long farmerId;
    private LocalDate appointmentDate;
    private String appointmentTime;
    private String topic;
    private String description;
    private Integer status;
    private Integer rating;
    private String feedback;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

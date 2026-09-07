package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("follow_up_record")
public class FollowUpRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long warningId;
    private Long elderId;
    private LocalDate followDate;
    private String followMethod;
    private String followContent;
    private Long doctorId;
    private Integer status;
    private LocalDate nextFollowDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

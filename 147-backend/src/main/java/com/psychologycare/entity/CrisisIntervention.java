package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("crisis_intervention")
public class CrisisIntervention {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String interventionNo;
    private String caseTheme;
    private String interventionType;
    private String targetPerson;
    private String interventionResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








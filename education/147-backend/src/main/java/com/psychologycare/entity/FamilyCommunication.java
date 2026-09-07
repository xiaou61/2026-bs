package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("family_communication")
public class FamilyCommunication {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String communicationNo;
    private String caseTheme;
    private String communicationTopic;
    private String communicationMethod;
    private String communicationTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








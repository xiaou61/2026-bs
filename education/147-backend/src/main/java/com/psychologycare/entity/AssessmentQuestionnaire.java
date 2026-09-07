package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("assessment_questionnaire")
public class AssessmentQuestionnaire {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String questionnaireNo;
    private String caseTheme;
    private String questionnaireName;
    private String submitTime;
    private String targetGroup;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








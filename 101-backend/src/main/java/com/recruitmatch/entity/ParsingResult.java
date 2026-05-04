package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("parsing_result")
public class ParsingResult {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long taskId;
    private Long resumeId;
    private Long candidateId;
    private String extractedEducation;
    private String extractedSkills;
    private String extractedExperience;
    private BigDecimal score;
    private String conclusion;
    private Integer reviewStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("candidate_profile")
public class CandidateProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String realName;
    private String gender;
    private String education;
    private Integer workYears;
    private String targetJob;
    private String skillTags;
    private BigDecimal expectedSalary;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

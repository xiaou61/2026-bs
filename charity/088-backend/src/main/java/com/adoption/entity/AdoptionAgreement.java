package com.adoption.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("adoption_agreement")
public class AdoptionAgreement {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String agreementNo;
    private Long applicationId;
    private Long childId;
    private Long applicantId;
    private Integer signStatus;
    private LocalDate signDate;
    private String agreementContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String childName;
    @TableField(exist = false)
    private String applicantName;
}

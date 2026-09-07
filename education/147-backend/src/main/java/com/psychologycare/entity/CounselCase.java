package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("counsel_case")
public class CounselCase {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String caseNo;
    private String caseTheme;
    private String issueType;
    private String collegeName;
    private String submitTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








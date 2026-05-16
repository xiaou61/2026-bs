package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("counsel_record")
public class CounselRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String caseTheme;
    private String counselorName;
    private String counselTime;
    private String conclusionSummary;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








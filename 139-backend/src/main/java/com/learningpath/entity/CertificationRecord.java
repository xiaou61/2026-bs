package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("certification_record")
public class CertificationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String certNo;
    private String learnerName;
    private String certificationName;
    private String issuerName;
    private String issueDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}




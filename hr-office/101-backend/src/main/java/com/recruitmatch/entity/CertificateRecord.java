package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("certificate_record")
public class CertificateRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long candidateId;
    private String certName;
    private String certType;
    private String issueOrg;
    private String certUrl;
    private Integer verifyStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

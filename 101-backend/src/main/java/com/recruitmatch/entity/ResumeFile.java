package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("resume_file")
public class ResumeFile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long candidateId;
    private String fileName;
    private String fileUrl;
    private String resumeText;
    private String education;
    private String skills;
    private Integer workYears;
    private Integer parseStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

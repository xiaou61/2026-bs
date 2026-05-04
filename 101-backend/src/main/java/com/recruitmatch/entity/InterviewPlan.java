package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("interview_plan")
public class InterviewPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long candidateId;
    private Long jobId;
    private Long interviewerId;
    private LocalDateTime interviewTime;
    private String interviewType;
    private String meetingAddress;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("job_requirement")
public class JobRequirement {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long jobId;
    private String requirementType;
    private String keyword;
    private BigDecimal weight;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

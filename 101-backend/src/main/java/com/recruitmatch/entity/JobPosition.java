package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("job_position")
public class JobPosition {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String jobName;
    private String department;
    private String jobType;
    private String location;
    private String salaryRange;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.alumni.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("activity")
public class Activity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String cover;
    private String address;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime signDeadline;
    private Integer maxCount;
    private Integer currentCount;
    private Integer status;
    private Long organizerId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String organizerName;
    @TableField(exist = false)
    private Boolean signed;
}

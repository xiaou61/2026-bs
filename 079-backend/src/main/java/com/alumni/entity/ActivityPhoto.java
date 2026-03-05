package com.alumni.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("activity_photo")
public class ActivityPhoto {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long activityId;
    private String url;
    private String description;
    private Long uploadUserId;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String uploadUserName;
}

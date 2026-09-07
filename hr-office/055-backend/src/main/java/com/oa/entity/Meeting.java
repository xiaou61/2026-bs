package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("meeting")
public class Meeting {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Long roomId;
    private Long organizerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String participants;
    private String content;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String roomName;
    @TableField(exist = false)
    private String organizerName;
}

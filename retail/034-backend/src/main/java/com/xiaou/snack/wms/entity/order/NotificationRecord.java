package com.xiaou.snack.wms.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("notification_record")
public class NotificationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String type;
    private String target;
    private String content;
    private String status;
    private LocalDateTime createdAt;
}

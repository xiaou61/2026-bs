package com.wallpaper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wallpaper_audit")
public class WallpaperAudit {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long wallpaperId;
    private Integer auditStatus;
    private String auditRemark;
    private Long auditorId;
    private LocalDateTime auditTime;
}

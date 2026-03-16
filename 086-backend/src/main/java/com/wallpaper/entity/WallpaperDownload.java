package com.wallpaper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wallpaper_download")
public class WallpaperDownload {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long wallpaperId;
    private Long userId;
    private String source;
    private LocalDateTime createTime;
}

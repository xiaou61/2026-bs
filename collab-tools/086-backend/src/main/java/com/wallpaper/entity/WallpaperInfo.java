package com.wallpaper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wallpaper_info")
public class WallpaperInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String subtitle;
    private Long categoryId;
    private String coverUrl;
    private String imageUrl;
    private String previewUrl;
    private String wallpaperType;
    private String resolution;
    private Integer width;
    private Integer height;
    private String colorHex;
    private String fileFormat;
    private Long fileSize;
    private String sourceType;
    private String authorName;
    private Long uploaderId;
    private String description;
    private Integer downloadCount;
    private Integer favoriteCount;
    private Integer viewCount;
    private Integer auditStatus;
    private Integer publishStatus;
    private Integer featured;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

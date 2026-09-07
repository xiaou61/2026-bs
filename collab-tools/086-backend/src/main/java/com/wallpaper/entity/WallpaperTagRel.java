package com.wallpaper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wallpaper_tag_rel")
public class WallpaperTagRel {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long wallpaperId;
    private Long tagId;
}

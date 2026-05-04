package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("image_asset")
public class ImageAsset {
    private Long id;
    private String title;
    private String imageUrl;
    private String promptText;
    private String modelName;
    private String category;
    private String description;
    private Long creatorId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

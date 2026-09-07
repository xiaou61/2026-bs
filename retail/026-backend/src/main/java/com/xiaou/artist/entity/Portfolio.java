package com.xiaou.artist.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Portfolio {
    private Long id;
    private Long artistId;
    private String title;
    private String description;
    private String imageUrl;
    private String category;
    private String tags;
    private Boolean isFeatured;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 关联字段
    private String artistName;
}

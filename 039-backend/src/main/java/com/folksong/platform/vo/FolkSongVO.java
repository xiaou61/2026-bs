package com.folksong.platform.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FolkSongVO {
    private Long id;
    private String title;
    private Long categoryId;
    private String categoryName;
    private Long userId;
    private String userName;
    private String userAvatar;
    private String content;
    private String lyrics;
    private String audioUrl;
    private String videoUrl;
    private String coverImage;
    private String region;
    private String ethnic;
    private String introduction;
    private Integer viewCount;
    private Integer likeCount;
    private Integer collectCount;
    private Integer commentCount;
    private Integer status;
    private Integer auditStatus;
    private Boolean isLiked;
    private Boolean isCollected;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.security.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ArticleVO {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String title;
    private String cover;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private Boolean isFavorite;
    private Boolean isLiked;
    private LocalDateTime createTime;
}

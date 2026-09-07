package com.blog.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostVO {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String cover;
    private Long categoryId;
    private String categoryName;
    private Long authorId;
    private String authorName;
    private Integer status;
    private Integer isTop;
    private Long viewCount;
    private Integer commentCount;
    private List<Long> tagIds;
    private List<String> tagNames;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

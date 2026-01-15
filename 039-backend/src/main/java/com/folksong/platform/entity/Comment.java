package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("comments")
public class Comment {
    @Id
    private Long id;
    private Long songId;
    private Long userId;
    private Long parentId;
    private String content;
    private Integer likeCount;
    private Integer status;
    private LocalDateTime createTime;
}

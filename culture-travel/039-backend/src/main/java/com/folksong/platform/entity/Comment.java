package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("comments")
public class Comment {
    @Id
    @Column("id")
    private Long id;
    @Column("song_id")
    private Long songId;
    @Column("user_id")
    private Long userId;
    @Column("parent_id")
    private Long parentId;
    @Column("content")
    private String content;
    @Column("like_count")
    private Integer likeCount;
    @Column("status")
    private Integer status;
    @Column("create_time")
    private LocalDateTime createTime;
}

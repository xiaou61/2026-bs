package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("folk_songs")
public class FolkSong {
    @Id
    @Column("id")
    private Long id;
    @Column("title")
    private String title;
    @Column("category_id")
    private Long categoryId;
    @Column("user_id")
    private Long userId;
    @Column("content")
    private String content;
    @Column("lyrics")
    private String lyrics;
    @Column("audio_url")
    private String audioUrl;
    @Column("video_url")
    private String videoUrl;
    @Column("cover_image")
    private String coverImage;
    @Column("region")
    private String region;
    @Column("ethnic")
    private String ethnic;
    @Column("introduction")
    private String introduction;
    @Column("view_count")
    private Integer viewCount;
    @Column("like_count")
    private Integer likeCount;
    @Column("collect_count")
    private Integer collectCount;
    @Column("comment_count")
    private Integer commentCount;
    @Column("status")
    private Integer status;
    @Column("audit_status")
    private Integer auditStatus;
    @Column("create_time")
    private LocalDateTime createTime;
    @Column("update_time")
    private LocalDateTime updateTime;
}

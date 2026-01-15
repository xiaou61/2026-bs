package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("folk_songs")
public class FolkSong {
    @Id
    private Long id;
    private String title;
    private Long categoryId;
    private Long userId;
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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

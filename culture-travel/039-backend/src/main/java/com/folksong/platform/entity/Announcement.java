package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("announcements")
public class Announcement {
    @Id
    @Column("id")
    private Long id;
    @Column("title")
    private String title;
    @Column("content")
    private String content;
    @Column("publisher_id")
    private Long publisherId;
    @Column("status")
    private Integer status;
    @Column("create_time")
    private LocalDateTime createTime;
    @Column("update_time")
    private LocalDateTime updateTime;
}

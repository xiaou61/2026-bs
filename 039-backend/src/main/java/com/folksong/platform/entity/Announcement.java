package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("announcements")
public class Announcement {
    @Id
    private Long id;
    private String title;
    private String content;
    private Long publisherId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

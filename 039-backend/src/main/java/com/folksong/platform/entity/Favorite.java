package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("favorites")
public class Favorite {
    @Id
    private Long id;
    private Long userId;
    private Long songId;
    private LocalDateTime createTime;
}

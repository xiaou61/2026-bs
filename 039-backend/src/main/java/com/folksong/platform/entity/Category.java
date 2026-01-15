package com.folksong.platform.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("categories")
public class Category {
    @Id
    private Long id;
    private String name;
    private String description;
    private String region;
    private String coverImage;
    private Integer sortOrder;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

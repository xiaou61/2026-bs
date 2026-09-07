package com.xiaou.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FoundItem {
    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime foundTime;
    private String foundLocation;
    private String contactName;
    private String contactPhone;
    private Integer status;
    private Integer views;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private String categoryName;
    private String userName;
    private List<ItemImage> images;
    private Boolean isFavorite;
}


package com.hospital.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BannerInfo {
    private Long id;
    private String title;
    private String imageUrl;
    private String linkUrl;
    private String description;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

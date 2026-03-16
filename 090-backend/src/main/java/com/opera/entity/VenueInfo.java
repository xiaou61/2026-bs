package com.opera.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VenueInfo {
    private Long id;
    private Long majorId;
    private String name;
    private String counselorName;
    private Integer memberCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}



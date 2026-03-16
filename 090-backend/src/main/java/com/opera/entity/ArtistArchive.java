package com.opera.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArtistArchive {
    private Long id;
    private Long departmentId;
    private String name;
    private String code;
    private String gradeYear;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}



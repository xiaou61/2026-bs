package com.student.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeVO {
    private Long id;
    private String title;
    private String content;
    private Long publisherId;
    private String publisherName;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

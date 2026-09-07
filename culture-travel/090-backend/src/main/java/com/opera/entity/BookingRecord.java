package com.opera.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRecord {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private Long memberId;
    private Integer selectStatus;
    private String courseName;
    private String memberName;
    private String scheduleInfo;
    private LocalDateTime createTime;
}



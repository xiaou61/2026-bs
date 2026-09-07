package com.opera.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CheckinRecord {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private Long memberId;
    private Long artistId;
    private LocalDate attendanceDate;
    private String attendanceStatus;
    private String remark;
    private String courseName;
    private String memberName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}



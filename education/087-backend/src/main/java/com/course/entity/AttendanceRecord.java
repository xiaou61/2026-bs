package com.course.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceRecord {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private Long studentId;
    private Long teacherId;
    private LocalDate attendanceDate;
    private String attendanceStatus;
    private String remark;
    private String courseName;
    private String studentName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

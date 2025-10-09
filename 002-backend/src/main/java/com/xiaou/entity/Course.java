package com.xiaou.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Course {
    private Long id;
    private String courseNo;
    private String courseName;
    private Long teacherId;
    private String teacherName;
    private BigDecimal credit;
    private Integer totalCapacity;
    private Integer selectedCount;
    private String courseType;
    private String semester;
    private String classroom;
    private String timeSlot;
    private String description;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedTime;
}


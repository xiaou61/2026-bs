package com.student.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class JobVO {
    private Long id;
    private String title;
    private String company;
    private String city;
    private String salary;
    private LocalDate deadline;
    private Long publisherId;
    private String publisherName;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.kindergarten.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ChildProfile {
    private Long id;
    private String childName;
    private String gender;
    private LocalDate birthday;
    private Long campusId;
    private Long gradeId;
    private Long classId;
    private Long parentId;
    private Long teacherId;
    private String allergyInfo;
    private String pickupPerson;
    private Integer profileStatus;
    private String campusName;
    private String gradeName;
    private String className;
    private String parentName;
    private String teacherName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

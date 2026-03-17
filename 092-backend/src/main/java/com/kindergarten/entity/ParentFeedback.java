package com.kindergarten.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParentFeedback {
    private Long id;
    private Long childId;
    private Long parentId;
    private Long teacherId;
    private String feedbackType;
    private Integer feedbackScore;
    private String feedbackContent;
    private String replyContent;
    private String childName;
    private String parentName;
    private String teacherName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

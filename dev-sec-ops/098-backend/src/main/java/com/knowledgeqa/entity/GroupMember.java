package com.knowledgeqa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupMember {
    private Long id;
    private Long groupId;
    private Long userId;
    private String memberRole;
    private Integer status;
    private LocalDateTime createTime;
}

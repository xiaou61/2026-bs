package com.xiaou.campusclub.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClubVO {
    private Long id;
    private String name;
    private String category;
    private String logo;
    private String cover;
    private String description;
    private String presidentName;
    private Integer memberCount;
    private Integer maxMembers;
    private Integer status;
    private Integer isRecruiting;
    private String recruitInfo;
    private Boolean isMember;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}


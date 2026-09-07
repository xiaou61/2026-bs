package com.xiaou.campusclub.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityVO {
    private Long id;
    private Long clubId;
    private String clubName;
    private String title;
    private String cover;
    private String description;
    private String location;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    private Integer maxParticipants;
    private Integer currentParticipants;
    private Integer status;
    private Integer points;
    private Boolean isRegistered;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}


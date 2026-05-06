package com.topicselect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TopicRelease {
    private Long id;
    private String projectNo;
    private String projectName;
    private String leaderName;
    private String collegeName;
    private Integer startYear;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


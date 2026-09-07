package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WorkTeam {
    private Long id;
    private String teamNo;
    private String teamName;
    private String leaderName;
    private Integer workerCount;
    private String workType;
    private String contactPhone;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

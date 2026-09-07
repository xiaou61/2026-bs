package com.licensecheck.entity;

    import lombok.Data;
import java.time.LocalDateTime;

    @Data
    public class RectificationTask {
        private Long id;
    private Long issueId;
    private String assignee;
    private String actionPlan;
    private String progressNote;
    private String status;
    private String dueDate;
    private String finishedAt;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    }

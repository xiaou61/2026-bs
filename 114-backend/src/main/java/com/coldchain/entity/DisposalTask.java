package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DisposalTask {
    private Long id;
    private String taskNo;
    private String alertNo;
    private String handlerName;
    private String measureText;
    private String finishTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

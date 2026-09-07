package com.worksite.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EquipmentCheck {
    private Long id;
    private String checkNo;
    private String equipmentName;
    private String equipmentType;
    private String projectName;
    private String checkResult;
    private String inspectorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

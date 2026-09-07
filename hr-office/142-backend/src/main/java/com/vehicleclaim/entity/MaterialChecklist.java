package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaterialChecklist {
    private Long id;
    private String checklistNo;
    private String reportNo;
    private String materialType;
    private String materialDesc;
    private String submitTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

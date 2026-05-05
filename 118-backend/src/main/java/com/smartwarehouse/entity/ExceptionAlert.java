package com.smartwarehouse.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExceptionAlert {
    private Long id;
    private String alertNo;
    private String agvNo;
    private String locationNo;
    private String alertLevel;
    private String alertContent;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

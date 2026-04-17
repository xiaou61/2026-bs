package com.xiaou.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class Visitor {
    private Integer id;
    private Integer ownerId;
    private String visitorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date visitTime;
    private String plateNumber;
    private String status; // PENDING, APPROVED, LEFT
    
    // Transient property for display
    private String ownerName;
}

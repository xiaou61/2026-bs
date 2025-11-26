package com.xiaou.community.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Visitor {
    private Integer id;
    private Integer ownerId;
    private String visitorName;
    private Date visitTime;
    private String plateNumber;
    private String status; // PENDING, APPROVED, LEFT
    
    // Transient property for display
    private String ownerName;
}

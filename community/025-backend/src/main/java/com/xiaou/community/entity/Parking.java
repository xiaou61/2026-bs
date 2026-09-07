package com.xiaou.community.entity;

import lombok.Data;

@Data
public class Parking {
    private Integer id;
    private String spotNumber;
    private String status; // FREE, SOLD, RENTED
    private Integer ownerId;
    
    // Transient property for display
    private String ownerName;
}

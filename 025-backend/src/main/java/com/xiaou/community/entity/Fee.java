package com.xiaou.community.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Fee {
    private Integer id;
    private Integer ownerId;
    private BigDecimal amount;
    private String type; // PROPERTY, PARKING
    private String status; // UNPAID, PAID
    private Date createTime;
    private Date payTime;
    
    // Transient property for display
    private String ownerName;
}

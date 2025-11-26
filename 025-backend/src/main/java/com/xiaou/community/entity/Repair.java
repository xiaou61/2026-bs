package com.xiaou.community.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Repair {
    private Integer id;
    private Integer ownerId;
    private String content;
    private String image;
    private String status; // PENDING, PROCESSING, COMPLETED
    private Date createTime;
    private Date finishTime;
    
    // Transient property for display
    private String ownerName;
}

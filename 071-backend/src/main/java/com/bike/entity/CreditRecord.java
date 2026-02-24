package com.bike.entity;

import lombok.Data;
import java.util.Date;

@Data
public class CreditRecord {
    private Long id;
    private Long userId;
    private Integer type;
    private Integer scoreChange;
    private Integer scoreAfter;
    private String description;
    private Date createTime;
}

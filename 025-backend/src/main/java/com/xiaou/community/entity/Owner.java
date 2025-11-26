package com.xiaou.community.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Owner {
    private Integer id;
    private Integer userId;
    private String name;
    private String phone;
    private String building;
    private String unit;
    private String room;
    private Date checkInTime;
}

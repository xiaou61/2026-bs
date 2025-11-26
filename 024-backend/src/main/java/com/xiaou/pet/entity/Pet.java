package com.xiaou.pet.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Pet {
    private Long id;
    private String name;
    private String type; // Cat, Dog, Other
    private String breed;
    private String age;
    private String gender; // Male, Female
    private String status; // AVAILABLE, PENDING, ADOPTED
    private String description;
    private String imageUrl;
    private String healthStatus;
    private String vaccineStatus;
    private Date createTime;
    private Date updateTime;
}

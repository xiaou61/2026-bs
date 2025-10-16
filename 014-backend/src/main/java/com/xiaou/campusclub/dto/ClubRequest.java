package com.xiaou.campusclub.dto;

import lombok.Data;

@Data
public class ClubRequest {
    private String name;
    private String category;
    private String logo;
    private String cover;
    private String description;
    private Integer maxMembers;
    private String recruitInfo;
}


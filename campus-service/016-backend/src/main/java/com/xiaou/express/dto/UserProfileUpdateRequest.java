package com.xiaou.express.dto;

import lombok.Data;

@Data
public class UserProfileUpdateRequest {
    private String username;
    private String dormitoryBuilding;
    private String dormitoryRoom;
}

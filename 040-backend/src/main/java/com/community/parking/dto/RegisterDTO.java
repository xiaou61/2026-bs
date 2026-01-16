package com.community.parking.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String phone;
    private String realName;
    private String roomNumber;
}

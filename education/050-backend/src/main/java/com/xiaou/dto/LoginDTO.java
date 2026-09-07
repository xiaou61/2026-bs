package com.xiaou.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class LoginDTO {
    private String username;
    @ToString.Exclude
    private String password;
}

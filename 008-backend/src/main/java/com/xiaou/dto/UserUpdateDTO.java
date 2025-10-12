package com.xiaou.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserUpdateDTO {
    private String nickname;
    private Integer gender;
    private Integer age;
    private BigDecimal height;
    private BigDecimal weight;
    private String tastePrefer;
    private String dietTaboo;
}


package com.bike.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phone;
    private String realName;
    private String avatar;
    private String role;
    private Integer creditScore;
    private BigDecimal balance;
    private Integer depositPaid;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

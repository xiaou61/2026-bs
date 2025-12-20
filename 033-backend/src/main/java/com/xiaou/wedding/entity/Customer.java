package com.xiaou.wedding.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Customer {
    private Long id;
    private Long userId;
    private String name;
    private Integer gender;
    private Integer age;
    private String phone;
    private String email;
    private String address;
    private String customerType;
    private String source;
    private String tags;
    private LocalDate birthday;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deleted;
}

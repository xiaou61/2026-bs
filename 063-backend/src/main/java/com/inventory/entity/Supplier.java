package com.inventory.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Supplier {
    private Long id;
    private String supplierNo;
    private String name;
    private String contactPerson;
    private String phone;
    private String address;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.movie.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Cinema {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

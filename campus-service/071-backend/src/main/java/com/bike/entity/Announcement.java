package com.bike.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

package com.movie.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private Long adminId;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

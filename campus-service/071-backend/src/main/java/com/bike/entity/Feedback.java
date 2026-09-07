package com.bike.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Feedback {
    private Long id;
    private Long userId;
    private Integer type;
    private String content;
    private String reply;
    private Integer status;
    private Date createTime;
    private String username;
}

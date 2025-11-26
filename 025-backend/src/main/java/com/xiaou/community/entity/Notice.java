package com.xiaou.community.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private Date createTime;
}

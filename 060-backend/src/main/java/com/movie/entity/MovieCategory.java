package com.movie.entity;

import lombok.Data;
import java.util.Date;

@Data
public class MovieCategory {
    private Long id;
    private String name;
    private Integer sort;
    private Integer status;
    private Date createTime;
}

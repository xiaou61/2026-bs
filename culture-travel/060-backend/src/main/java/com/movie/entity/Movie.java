package com.movie.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Movie {
    private Long id;
    private String title;
    private String poster;
    private String director;
    private String actors;
    private Long categoryId;
    private Integer duration;
    private Date releaseDate;
    private String description;
    private BigDecimal score;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    private String categoryName;
}

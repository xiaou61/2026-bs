package com.movie.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Review {
    private Long id;
    private Long userId;
    private Long movieId;
    private Integer rating;
    private String content;
    private Integer status;
    private Date createTime;

    private String username;
    private String movieTitle;
}

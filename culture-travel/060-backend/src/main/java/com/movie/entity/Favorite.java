package com.movie.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Favorite {
    private Long id;
    private Long userId;
    private Long movieId;
    private Date createTime;

    private String movieTitle;
    private String director;
    private String poster;
}

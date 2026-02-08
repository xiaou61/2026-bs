package com.movie.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Showtime {
    private Long id;
    private Long movieId;
    private Long cinemaId;
    private Long hallId;
    private Date showDate;
    private String startTime;
    private String endTime;
    private BigDecimal price;
    private Integer availableSeats;
    private Integer status;
    private Date createTime;

    private String movieTitle;
    private String cinemaName;
    private String hallName;
}

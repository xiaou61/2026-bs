package com.movie.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Hall {
    private Long id;
    private Long cinemaId;
    private String name;
    private Integer seatRows;
    private Integer seatCols;
    private String hallType;
    private Integer status;
    private Date createTime;

    private String cinemaName;
}

package com.movie.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TicketOrder {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long showtimeId;
    private String seats;
    private Integer seatCount;
    private BigDecimal totalPrice;
    private Integer status;
    private Date payTime;
    private Date createTime;
    private Date updateTime;

    private String username;
    private String movieTitle;
    private String cinemaName;
    private String hallName;
    private String showDate;
    private String startTime;
}

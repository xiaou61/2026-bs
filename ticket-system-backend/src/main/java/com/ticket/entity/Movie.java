package com.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("movie")
public class Movie {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String type;
    private String category;
    private Integer duration;
    private String director;
    private String actors;
    private String poster;
    private String description;
    private BigDecimal rating;
    private Integer commentCount;
    private String status;
    private Integer isRecommend;
    private LocalDate releaseDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

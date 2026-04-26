package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("venue_info")
public class VenueInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String venueNo;
    private String name;
    private String city;
    private String address;
    private Integer capacity;
    private String turfType;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

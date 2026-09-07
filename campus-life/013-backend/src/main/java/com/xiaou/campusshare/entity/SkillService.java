package com.xiaou.campusshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("skill_service")
public class SkillService {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String category;
    private String title;
    private String description;
    private String images;
    private String portfolio;
    private Integer serviceDuration;
    private BigDecimal hourlyPrice;
    private String availableTime;
    private String serviceLocation;
    private Integer locationType;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String introduction;
    private String certificate;
    private Integer viewCount;
    private Integer orderCount;
    private BigDecimal rating;
    private Integer status;
    private Integer isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}


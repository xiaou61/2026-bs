package com.harbin.tourism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("travel_note")
public class TravelNote {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String coverImg;
    private String tags;
    private Long spotId;
    private Integer likeCount;
    private Integer viewCount;
    private String status;
    private LocalDateTime createdAt;
}

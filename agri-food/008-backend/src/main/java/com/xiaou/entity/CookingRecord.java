package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("cooking_record")
public class CookingRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long recipeId;
    private String image;
    private String note;
    private Integer tasteRating;
    private Integer difficultyRating;
    private Integer timeRating;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}


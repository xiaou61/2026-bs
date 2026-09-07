package com.xiaou.herbal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("recipe")
public class Recipe implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private Long authorId;

    private String coverImage;

    private String efficacy;

    private String nutrition;

    private Integer difficulty;

    private Integer cookTime;

    private Integer servings;

    private String applicablePeople;

    private String seasons;

    private Integer status;

    private Integer views;

    private Integer likes;

    private Integer collects;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

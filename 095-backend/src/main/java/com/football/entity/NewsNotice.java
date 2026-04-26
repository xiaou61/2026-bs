package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("news_notice")
public class NewsNotice {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String noticeType;
    private Integer status;
    private LocalDateTime publishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String authorName;
}

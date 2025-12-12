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
@TableName("comment")
public class Comment implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer targetType;

    private Long targetId;

    private Long userId;

    private String content;

    private Integer rating;

    private String imageUrls;

    private Integer likes;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

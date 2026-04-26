package com.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("fan_follow")
public class FanFollow {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long teamId;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String teamName;
}

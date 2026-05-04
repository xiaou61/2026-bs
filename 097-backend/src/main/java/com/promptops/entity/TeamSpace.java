package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("team_space")
public class TeamSpace {
    private Long id;
    private String name;
    private String ownerName;
    private String description;
    private Integer memberCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

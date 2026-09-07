package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("rescue_team")
public class RescueTeam {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String teamNo;
    private String teamName;
    private String teamType;
    private Integer memberCount;
    private String contactPhone;
    private String baseLocation;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

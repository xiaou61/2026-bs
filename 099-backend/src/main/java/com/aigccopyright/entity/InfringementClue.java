package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("infringement_clue")
public class InfringementClue {
    private Long id;
    private Long assetId;
    private String platformName;
    private String clueUrl;
    private String description;
    private String evidenceUrl;
    private Integer status;
    private Long handlerId;
    private String handleComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("copyright_register")
public class CopyrightRegister {
    private Long id;
    private Long assetId;
    private String authorName;
    private String rightType;
    private String declaration;
    private Integer registerStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("pest_warning")
public class PestWarning {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Long pestDiseaseId;
    private String region;
    private String cropType;
    private Integer warningLevel;
    private LocalDate warningDate;
    private String content;
    private Long publisherId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

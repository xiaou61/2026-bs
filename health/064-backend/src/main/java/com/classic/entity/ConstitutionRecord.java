package com.classic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("constitution_record")
public class ConstitutionRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String constitutionType;
    private String symptomDesc;
    private String suggestion;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

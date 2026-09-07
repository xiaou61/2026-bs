package com.classic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("formula_info")
public class FormulaInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String source;
    private String indication;
    private String composition;
    private String usageMethod;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

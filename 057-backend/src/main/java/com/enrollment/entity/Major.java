package com.enrollment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("major")
public class Major {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long departmentId;
    private String name;
    private String code;
    private String degree;
    private Integer duration;
    private BigDecimal tuition;
    private String description;
    private String requirement;
    private Integer status;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String departmentName;
}

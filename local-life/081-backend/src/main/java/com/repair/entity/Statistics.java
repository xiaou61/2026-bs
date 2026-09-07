package com.repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("statistics")
public class Statistics {
    @TableId(type = IdType.AUTO)
    private Long id;
    private LocalDate statDate;
    private Integer totalChildren;
    private Integer sponsoredChildren;
    private Integer totalDonors;
    private BigDecimal totalAmount;
    private Integer totalProjects;
    private LocalDateTime createTime;
}


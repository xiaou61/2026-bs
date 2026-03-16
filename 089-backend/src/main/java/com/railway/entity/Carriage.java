package com.railway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("carriage_template")
public class Carriage {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String templateName;
    private String seatType;
    private Integer coachCount;
    private Integer seatRows;
    private Integer seatCols;
    private BigDecimal priceFactor;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

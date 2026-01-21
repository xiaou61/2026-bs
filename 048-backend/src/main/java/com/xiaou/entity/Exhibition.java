package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("exhibition")
public class Exhibition {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Long hallId;
    private String coverImage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private BigDecimal ticketPrice;
    private Integer viewCount;
    private Integer status; // 0-未开始,1-进行中,2-已结束
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(exist = false)
    private String hallName;
}

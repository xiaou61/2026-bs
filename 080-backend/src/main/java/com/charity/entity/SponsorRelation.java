package com.charity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("sponsor_relation")
public class SponsorRelation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long childId;
    private Long donorId;
    private String sponsorType;
    private BigDecimal sponsorAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

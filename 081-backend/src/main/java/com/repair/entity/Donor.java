package com.repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("donor")
public class Donor {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String donorType;
    private String companyName;
    private String contactPerson;
    private String contactPhone;
    private BigDecimal totalAmount;
    private Integer donationCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}


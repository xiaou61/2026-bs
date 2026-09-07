package com.charity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("donation")
public class Donation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long donorId;
    private Long childId;
    private Long projectId;
    private BigDecimal amount;
    private String donationType;
    private String materialDesc;
    private LocalDate donationDate;
    private String paymentMethod;
    private String certificateNo;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

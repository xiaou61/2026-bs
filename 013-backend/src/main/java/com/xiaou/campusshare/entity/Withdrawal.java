package com.xiaou.campusshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("withdrawal")
public class Withdrawal {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String withdrawalNo;
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal actualAmount;
    private String withdrawType;
    private String accountName;
    private String accountNo;
    private Integer status;
    private String rejectReason;
    private Long auditAdminId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transferTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}


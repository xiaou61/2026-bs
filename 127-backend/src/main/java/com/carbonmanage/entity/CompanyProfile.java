package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("company_profile")
public class CompanyProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String companyNo;
    private String companyName;
    private String industryName;
    private String regionName;
    private String contactName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

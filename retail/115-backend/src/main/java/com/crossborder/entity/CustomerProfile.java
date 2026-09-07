package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("customer_profile")
public class CustomerProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String customerName;
    private String customerNo;
    private String countryRegion;
    private String email;
    private String phone;
    private String identityNo;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

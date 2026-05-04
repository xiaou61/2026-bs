package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("customer_profile")
public class CustomerProfile {
    @TableId
    private Long id;
    private String customerName;
    private String phone;
    private String email;
    private String levelName;
    private String tags;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

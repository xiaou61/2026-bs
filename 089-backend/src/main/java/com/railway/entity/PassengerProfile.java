package com.railway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("passenger_profile")
public class PassengerProfile {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String passengerName;
    private String idCard;
    private String phone;
    private String passengerType;
    private Integer isDefault;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

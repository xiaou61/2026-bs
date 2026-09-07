package com.vending.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("vending_machine")
public class VendingMachine {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String machineNo;
    private Long locationId;
    private String name;
    private String temperatureType;
    private String status;
    private LocalDateTime lastReplenishTime;
    private LocalDateTime lastOnlineTime;
    private String managerName;
    private String managerPhone;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String locationName;
}

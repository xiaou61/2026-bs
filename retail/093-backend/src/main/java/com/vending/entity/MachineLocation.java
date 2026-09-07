package com.vending.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("machine_location")
public class MachineLocation {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String locationNo;
    private String name;
    private String sceneType;
    private String contactPerson;
    private String contactPhone;
    private String address;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

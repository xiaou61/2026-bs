package com.petcafe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("cafe_area")
public class CafeArea {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String areaNo;
    private String name;
    private String city;
    private String district;
    private String address;
    private String contactPerson;
    private String contactPhone;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

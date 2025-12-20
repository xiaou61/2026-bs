package com.xiaou.snack.wms.entity.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("location")
public class Location {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long zoneId;
    private String code;
    private Integer capacity;
}

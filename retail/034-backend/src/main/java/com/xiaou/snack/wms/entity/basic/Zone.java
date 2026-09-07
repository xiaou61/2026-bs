package com.xiaou.snack.wms.entity.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("zone")
public class Zone {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long warehouseId;
    private String name;
    private String type;
}

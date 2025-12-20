package com.xiaou.snack.wms.entity.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sku")
public class Sku {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String spec;
    private Integer shelfLifeDays;
    private String barcode;
    private Long supplierId;
}

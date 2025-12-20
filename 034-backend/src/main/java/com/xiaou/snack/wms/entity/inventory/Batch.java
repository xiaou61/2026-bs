package com.xiaou.snack.wms.entity.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("batch")
public class Batch {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long skuId;
    private String batchNo;
    private LocalDate productionDate;
    private LocalDate expireDate;
}

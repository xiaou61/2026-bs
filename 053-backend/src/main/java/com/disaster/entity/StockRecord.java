package com.disaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("stock_record")
public class StockRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long warehouseId;
    private Long materialId;
    private Integer type;
    private Integer quantity;
    private Integer beforeQuantity;
    private Integer afterQuantity;
    private String source;
    private Long operatorId;
    private String remark;
    private LocalDateTime createTime;
}

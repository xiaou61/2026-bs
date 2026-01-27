package com.disaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("dispatch_item")
public class DispatchItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long dispatchId;
    private Long materialId;
    private Integer quantity;
    private LocalDateTime createTime;
}

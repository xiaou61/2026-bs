package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("stock_warning")
public class PublicNotice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String warningNo;
    private String consumableName;
    private Integer currentQty;
    private String warningLevel;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







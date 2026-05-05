package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("spare_part_inbound")
public class SparePartInbound {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String inboundNo;
    private String partName;
    private String supplierName;
    private Integer inboundQty;
    private String qualityResult;
    private String inboundTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("spare_part_outbound")
public class SparePartOutbound {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String outboundNo;
    private String partName;
    private String equipmentName;
    private String receiverName;
    private Integer outboundQty;
    private String purposeText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

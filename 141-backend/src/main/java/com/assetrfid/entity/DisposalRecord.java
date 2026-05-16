package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("disposal_record")
public class DisposalRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String disposalNo;
    private String assetName;
    private Integer disposalQty;
    private String disposalType;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

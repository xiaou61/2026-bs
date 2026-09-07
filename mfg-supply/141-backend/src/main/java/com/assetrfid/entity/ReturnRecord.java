package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("return_record")
public class ReturnRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String returnNo;
    private String applicationNo;
    private String assetName;
    private Integer returnQty;
    private String operatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

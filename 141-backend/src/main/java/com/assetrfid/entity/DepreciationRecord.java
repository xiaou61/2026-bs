package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("depreciation_record")
public class DepreciationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String depreciationNo;
    private String assetName;
    private String departmentName;
    private Integer originalValue;
    private Integer netValue;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

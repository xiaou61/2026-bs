package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("asset_info")
public class AssetInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String assetNo;
    private String assetName;
    private String assetModel;
    private String departmentName;
    private Integer serviceLifeMonths;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

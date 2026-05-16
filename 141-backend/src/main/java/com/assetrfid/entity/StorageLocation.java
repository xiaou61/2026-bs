package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("storage_location")
public class StorageLocation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String locationNo;
    private String assetName;
    private String locationName;
    private Integer currentQty;
    private Integer lockedQty;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

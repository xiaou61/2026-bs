package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("rfid_tag")
public class RfidTag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String tagNo;
    private String epcCode;
    private String assetName;
    private String storageArea;
    private String managerPhone;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

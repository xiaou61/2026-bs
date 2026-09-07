package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("laboratory_room")
public class LaboratoryRoom {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String labNo;
    private String labName;
    private String labType;
    private String campusName;
    private Integer capacity;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

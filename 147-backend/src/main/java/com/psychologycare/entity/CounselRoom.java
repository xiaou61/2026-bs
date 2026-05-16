package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("counsel_room")
public class CounselRoom {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String roomNo;
    private String roomName;
    private String roomType;
    private String campusName;
    private Integer capacityLimit;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

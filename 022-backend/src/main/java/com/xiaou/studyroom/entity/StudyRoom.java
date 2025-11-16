package com.xiaou.studyroom.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("study_room")
public class StudyRoom {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("room_name")
    private String roomName;

    @TableField("floor_number")
    private Integer floorNumber;

    @TableField("capacity")
    private Integer capacity;

    @TableField("open_time")
    private LocalTime openTime;

    @TableField("close_time")
    private LocalTime closeTime;

    @TableField("description")
    private String description;

    @TableField("status")
    private Integer status;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}
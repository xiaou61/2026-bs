package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("system_notice")
public class SystemNotice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String noticeNo;
    private String equipmentName;
    private String noticeType;
    private String noticeContent;
    private String publishTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}









package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("warning_notice")
public class WarningNotice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String noticeNo;
    private String assetName;
    private Integer remainingCount;
    private String warningType;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

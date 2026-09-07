package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("public_notice")
public class PublicNotice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String noticeNo;
    private String complaintTitle;
    private String noticeType;
    private String noticeContent;
    private String publishTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







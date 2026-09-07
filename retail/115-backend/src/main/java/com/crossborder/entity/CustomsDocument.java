package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("customs_document")
public class CustomsDocument {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String documentNo;
    private String orderNo;
    private String documentType;
    private String fileUrl;
    private String auditUser;
    private String auditResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

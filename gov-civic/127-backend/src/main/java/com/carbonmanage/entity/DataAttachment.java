package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("data_attachment")
public class DataAttachment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String attachNo;
    private String companyNo;
    private String fileName;
    private String fileType;
    private String uploaderName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

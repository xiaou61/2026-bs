package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("access_request")
public class AccessRequest {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String requestNo;
    private String requesterName;
    private String datasetName;
    private String purposeText;
    private LocalDateTime expireTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

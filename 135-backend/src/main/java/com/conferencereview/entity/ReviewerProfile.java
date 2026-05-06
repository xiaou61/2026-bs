package com.conferencereview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("purchase_request")
public class ReviewerProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String requestNo;
    private String consumableName;
    private Integer requestQty;
    private String applicantName;
    private String requestTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


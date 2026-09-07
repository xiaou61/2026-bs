package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("complaint_ticket")
public class ComplaintTicket {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ticketNo;
    private String consumerName;
    private String merchantName;
    private String issueType;
    private String detailText;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

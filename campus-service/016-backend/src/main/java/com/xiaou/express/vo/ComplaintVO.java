package com.xiaou.express.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintVO {
    private Long id;
    private Long orderId;
    private String orderNo;
    private Long complainantId;
    private String complainantName;
    private Long respondentId;
    private String respondentName;
    private String type;
    private String reason;
    private String evidence;
    private Integer status;
    private String handleResult;
    private Long handleAdminId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime handleTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}

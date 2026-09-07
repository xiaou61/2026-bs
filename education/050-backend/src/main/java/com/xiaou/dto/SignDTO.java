package com.xiaou.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SignDTO {
    private Long taskId;
    private String signCode; // 数字签到码
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private String deviceInfo;
}

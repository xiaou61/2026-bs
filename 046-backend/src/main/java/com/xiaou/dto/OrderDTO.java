package com.xiaou.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private String address;
    private String contactName;
    private String contactPhone;
    private LocalDateTime appointmentTime;
    private String categoryIds;
    private BigDecimal estimatedWeight;
    private String remark;
    private List<OrderDetailDTO> details;

    @Data
    public static class OrderDetailDTO {
        private Long categoryId;
        private BigDecimal weight;
    }
}
